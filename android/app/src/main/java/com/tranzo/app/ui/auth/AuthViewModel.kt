package com.tranzo.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tranzo.app.data.api.TranzoApi
import com.tranzo.app.data.model.GoogleLoginRequest
import com.tranzo.app.data.model.SendOtpRequest
import com.tranzo.app.data.model.UpdateProfileRequest
import com.tranzo.app.data.model.VerifyOtpRequest
import com.tranzo.app.util.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class AuthMethod {
    EMAIL_OTP, GOOGLE, BIOMETRIC, PASSKEY
}

data class AuthUiState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val isNewUser: Boolean = false,
    val isProfileSaved: Boolean = false,
    val error: String? = null,
    val otpSent: Boolean = false,
    val authMethod: AuthMethod? = null,
    val lastEmail: String? = null,
    val biometricEnabled: Boolean = false,
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val api: TranzoApi,
    private val sessionManager: SessionManager,
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state = _state.asStateFlow()

    val isLoggedIn: Boolean
        get() = sessionManager.isLoggedIn()

    fun sendOtp(email: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                api.sendOtp(SendOtpRequest(email))
                _state.value = _state.value.copy(isLoading = false, otpSent = true)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to send OTP",
                )
            }
        }
    }

    fun verifyOtp(email: String, otp: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val response = api.verifyOtp(VerifyOtpRequest(email, otp))

                // Save tokens and email
                saveTokens(response.accessToken, response.refreshToken)
                sessionManager.saveUserData(
                    userId = "",  // Will be set after profile setup
                    email = email,
                )

                _state.value = _state.value.copy(
                    isLoading = false,
                    isAuthenticated = true,
                    isNewUser = response.isNewUser,
                    authMethod = AuthMethod.EMAIL_OTP,
                    lastEmail = email,
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Invalid OTP",
                )
            }
        }
    }

    fun loginWithGoogle(idToken: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val response = api.loginWithGoogle(GoogleLoginRequest(idToken))

                // Save tokens and email
                saveTokens(response.accessToken, response.refreshToken)
                sessionManager.saveUserData(
                    userId = "",  // Will be set after profile setup or returned by API
                    email = "",  // Will be filled from Google profile or profile setup
                )

                _state.value = _state.value.copy(
                    isLoading = false,
                    isAuthenticated = true,
                    isNewUser = response.isNewUser,
                    authMethod = AuthMethod.GOOGLE,
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Google login failed",
                )
            }
        }
    }

    fun biometricLogin(email: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                // For biometric login, check if user has stored session
                if (sessionManager.isLoggedIn()) {
                    // In a real implementation, would call a refresh endpoint
                    // For now, just mark as authenticated if token exists
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isAuthenticated = true,
                        isNewUser = false,  // Biometric only for returning users
                        authMethod = AuthMethod.BIOMETRIC,
                        lastEmail = email,
                    )
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Biometric login failed. Please use email instead.",
                    )
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Biometric login failed",
                )
            }
        }
    }


    fun logout() {
        viewModelScope.launch {
            try {
                api.logout()
            } catch (_: Exception) { }
            sessionManager.clearSession()
            _state.value = AuthUiState()
        }
    }

    fun saveProfile(
        firstName: String,
        lastName: String,
        email: String,
        phone: String = "",
        language: String = "en",
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                api.updateProfile(
                    UpdateProfileRequest(
                        firstName = firstName,
                        lastName = lastName,
                        displayName = "$firstName $lastName".trim(),
                        // TODO: Add phone and language to UpdateProfileRequest model
                        // phone = phone.ifBlank { null },
                        // language = language,
                    )
                )
                _state.value = _state.value.copy(
                    isLoading = false,
                    isProfileSaved = true,
                )
                // Save profile details locally for later use
                saveProfileLocally(firstName, lastName, phone, language)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to save profile",
                )
            }
        }
    }

    fun shouldShowProfileSetup(): Boolean {
        return _state.value.isNewUser && !_state.value.isProfileSaved
    }

    fun enableBiometric() {
        _state.value = _state.value.copy(biometricEnabled = true)
        // Biometric preference is stored in system keystore
        // This is handled by BiometricHelper, not SharedPreferences
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }

    private fun saveTokens(accessToken: String, refreshToken: String) {
        sessionManager.saveTokens(accessToken, refreshToken)
    }

    private fun clearTokens() {
        sessionManager.clearSession()
    }

    private fun saveLastEmail(email: String) {
        // SessionManager saves email through saveUserData
        sessionManager.saveUserData(userId = "", email = email)
    }

    fun getLastEmail(): String? {
        return sessionManager.getEmail()
    }

    private fun saveProfileLocally(
        firstName: String,
        lastName: String,
        phone: String,
        language: String,
    ) {
        val currentProfile = sessionManager.getUserProfile()
        sessionManager.saveUserData(
            userId = currentProfile?.userId ?: "",
            email = currentProfile?.email ?: "",
            firstName = firstName,
            lastName = lastName,
            phone = phone,
            avatarUrl = currentProfile?.avatarUrl,
            walletAddress = currentProfile?.walletAddress,
        )
    }

    fun getProfileLocally(): Pair<String, String> {
        val profile = sessionManager.getUserProfile()
        return (profile?.firstName ?: "") to (profile?.lastName ?: "")
    }
}
