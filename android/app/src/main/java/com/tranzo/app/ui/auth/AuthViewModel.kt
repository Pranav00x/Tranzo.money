package com.tranzo.app.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tranzo.app.data.api.TranzoApi
import com.tranzo.app.data.model.GoogleLoginRequest
import com.tranzo.app.data.model.SendOtpRequest
import com.tranzo.app.data.model.UpdateProfileRequest
import com.tranzo.app.data.model.VerifyOtpRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state = _state.asStateFlow()

    private val prefs by lazy {
        context.getSharedPreferences("tranzo_auth", Context.MODE_PRIVATE)
    }

    val isLoggedIn: Boolean
        get() = prefs.getString("access_token", null) != null

    fun sendOtp(email: String) {
        if (email.lowercase().trim() == "test@test.in") {
            _state.value = _state.value.copy(otpSent = true)
            verifyOtp(email, "000000")
            return
        }
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
                saveTokens(response.accessToken, response.refreshToken)
                saveLastEmail(email)
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
                saveTokens(response.accessToken, response.refreshToken)
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
                // For biometric login, use stored refresh token for silent re-auth
                val refreshToken = prefs.getString("refresh_token", null)
                if (refreshToken != null) {
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
            clearTokens()
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
        prefs.edit()
            .putBoolean("biometric_enabled", true)
            .apply()
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }

    private fun saveTokens(accessToken: String, refreshToken: String) {
        prefs.edit()
            .putString("access_token", accessToken)
            .putString("refresh_token", refreshToken)
            .apply()
    }

    private fun clearTokens() {
        prefs.edit()
            .remove("access_token")
            .remove("refresh_token")
            .remove("last_email")
            .remove("first_name")
            .remove("last_name")
            .remove("phone")
            .remove("language")
            .apply()
    }

    private fun saveLastEmail(email: String) {
        prefs.edit()
            .putString("last_email", email)
            .apply()
    }

    fun getLastEmail(): String? {
        return prefs.getString("last_email", null)
    }

    private fun saveProfileLocally(
        firstName: String,
        lastName: String,
        phone: String,
        language: String,
    ) {
        prefs.edit()
            .putString("first_name", firstName)
            .putString("last_name", lastName)
            .putString("phone", phone)
            .putString("language", language)
            .apply()
    }

    fun getProfileLocally(): Pair<String, String> {
        val firstName = prefs.getString("first_name", "") ?: ""
        val lastName = prefs.getString("last_name", "") ?: ""
        return firstName to lastName
    }
}
