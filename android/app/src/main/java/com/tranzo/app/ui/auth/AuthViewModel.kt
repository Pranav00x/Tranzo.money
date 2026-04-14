package com.tranzo.app.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tranzo.app.data.api.TranzoApi
import com.tranzo.app.data.model.SendOtpRequest
import com.tranzo.app.data.model.VerifyOtpRequest
import com.tranzo.app.data.model.GoogleLoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthUiState(
    val isLoading: Boolean = false,
    val isAuthenticated: Boolean = false,
    val isNewUser: Boolean = false,
    val error: String? = null,
    val otpSent: Boolean = false,
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
                _state.value = _state.value.copy(
                    isLoading = false,
                    isAuthenticated = true,
                    isNewUser = response.isNewUser,
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
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Google login failed",
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
            .apply()
    }
}
