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
import androidx.credentials.CredentialManager
import androidx.credentials.CreatePublicKeyCredentialRequest
import androidx.credentials.CreatePublicKeyCredentialResponse
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPublicKeyCredentialOption
import androidx.credentials.PublicKeyCredential
import com.google.gson.Gson

enum class AuthMethod {
    EMAIL_OTP, GOOGLE, BIOMETRIC, PASSKEY, TWITTER
}

enum class WalletStage {
    IDLE, GENERATING, DEPLOYING, ACTIVATING, COMPLETE
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
    val walletStage: WalletStage = WalletStage.IDLE,
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

                // Save tokens
                saveTokens(response.accessToken, response.refreshToken)

                // Fetch current user data to get userId and other profile info
                val userResponse = api.getMe()
                sessionManager.saveUserData(
                    userId = userResponse.id,
                    email = userResponse.email ?: email,
                    firstName = userResponse.firstName,
                    lastName = userResponse.lastName,
                    phone = userResponse.phone,
                    avatarUrl = userResponse.avatarUrl,
                    walletAddress = userResponse.smartAccount,
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

                // Save tokens
                saveTokens(response.accessToken, response.refreshToken)

                // Fetch current user data to get userId and profile info
                val userResponse = api.getMe()
                sessionManager.saveUserData(
                    userId = userResponse.id,
                    email = userResponse.email ?: "",
                    firstName = userResponse.firstName,
                    lastName = userResponse.lastName,
                    phone = userResponse.phone,
                    avatarUrl = userResponse.avatarUrl,
                    walletAddress = userResponse.smartAccount,
                )

                _state.value = _state.value.copy(
                    isLoading = false,
                    isAuthenticated = true,
                    isNewUser = response.isNewUser,
                    authMethod = AuthMethod.GOOGLE,
                    lastEmail = userResponse.email ?: "",
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Google login failed",
                )
            }
        }
    }

    fun loginWithTwitter(twitterId: String, email: String?, name: String?) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val request = com.tranzo.app.data.model.TwitterLoginRequest(twitterId, email, name)
                val response = api.loginWithTwitter(request)

                // Save tokens
                saveTokens(response.accessToken, response.refreshToken)

                // Fetch current user data
                val userResponse = api.getMe()
                sessionManager.saveUserData(
                    userId = userResponse.id,
                    email = userResponse.email ?: email ?: "",
                    firstName = userResponse.firstName,
                    lastName = userResponse.lastName,
                    phone = userResponse.phone,
                    avatarUrl = userResponse.avatarUrl,
                    walletAddress = userResponse.smartAccount,
                )

                _state.value = _state.value.copy(
                    isLoading = false,
                    isAuthenticated = true,
                    isNewUser = response.isNewUser,
                    authMethod = AuthMethod.TWITTER,
                    lastEmail = userResponse.email ?: email ?: "",
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Twitter login failed",
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

    fun registerPasskey(context: android.content.Context) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                // 1. Get options from backend
                val optionsJson = api.getPasskeyRegisterOptions()
                
                // 2. Use Credential Manager to create passkey
                val credentialManager = CredentialManager.create(context)
                val request = CreatePublicKeyCredentialRequest(
                    requestJson = Gson().toJson(optionsJson)
                )
                
                val result = credentialManager.createCredential(
                    context = context,
                    request = request
                )
                
                // 3. Verify with backend
                val responseJson = result.data.getString("androidx.credentials.BUNDLE_KEY_REGISTRATION_RESPONSE_JSON")
                val verifyMap = Gson().fromJson(responseJson, Map::class.java) as Map<String, Any>
                api.verifyPasskeyRegister(verifyMap)
                
                _state.value = _state.value.copy(isLoading = false, authMethod = AuthMethod.PASSKEY)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Passkey registration failed"
                )
            }
        }
    }

    fun loginWithPasskey(context: android.content.Context, email: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                // 1. Get options from backend
                val optionsJson = api.getPasskeyLoginOptions(mapOf("email" to email))
                
                // 2. Use Credential Manager to get passkey
                val credentialManager = CredentialManager.create(context)
                val getOption = GetPublicKeyCredentialOption(
                    requestJson = Gson().toJson(optionsJson)
                )
                
                val request = GetCredentialRequest(
                    listOf(getOption)
                )
                
                val result = credentialManager.getCredential(
                    context = context,
                    request = request
                )
                
                // 3. Verify with backend
                val responseJson = (result.credential as PublicKeyCredential).data.getString("androidx.credentials.BUNDLE_KEY_AUTHENTICATION_RESPONSE_JSON")
                val verifyMap = Gson().fromJson(responseJson, Map::class.java) as Map<String, Any>
                val authResponse = api.verifyPasskeyLogin(verifyMap)
                
                // 4. Save session
                saveTokens(authResponse.accessToken, authResponse.refreshToken)
                val userResponse = api.getMe()
                sessionManager.saveUserData(
                    userId = userResponse.id,
                    email = userResponse.email ?: email,
                    firstName = userResponse.firstName,
                    lastName = userResponse.lastName,
                    walletAddress = userResponse.smartAccount
                )
                
                _state.value = _state.value.copy(
                    isLoading = false,
                    isAuthenticated = true,
                    authMethod = AuthMethod.PASSKEY
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Passkey login failed"
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

    fun createWallet() {
        viewModelScope.launch {
            _state.value = _state.value.copy(walletStage = WalletStage.GENERATING)
            kotlinx.coroutines.delay(1500)
            _state.value = _state.value.copy(walletStage = WalletStage.DEPLOYING)
            kotlinx.coroutines.delay(2000)
            _state.value = _state.value.copy(walletStage = WalletStage.ACTIVATING)
            kotlinx.coroutines.delay(1500)
            _state.value = _state.value.copy(walletStage = WalletStage.COMPLETE)
        }
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
