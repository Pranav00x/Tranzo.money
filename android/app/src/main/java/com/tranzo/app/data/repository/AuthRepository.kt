package com.tranzo.app.data.repository

import android.content.Context
import com.tranzo.app.data.api.TranzoApi
import com.tranzo.app.data.model.*
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: TranzoApi,
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences("tranzo_auth", Context.MODE_PRIVATE)

    suspend fun sendOtp(email: String): Result<MessageResponse> = runCatching {
        api.sendOtp(SendOtpRequest(email))
    }

    suspend fun verifyOtp(email: String, otp: String): Result<AuthResponse> = runCatching {
        val response = api.verifyOtp(VerifyOtpRequest(email, otp))
        saveTokens(response.accessToken, response.refreshToken)
        response
    }

    suspend fun logout(): Result<MessageResponse> = runCatching {
        val response = api.logout()
        clearTokens()
        response
    }

    fun saveTokens(accessToken: String, refreshToken: String) {
        prefs.edit()
            .putString("access_token", accessToken)
            .putString("refresh_token", refreshToken)
            .apply()
    }

    fun clearTokens() {
        prefs.edit()
            .remove("access_token")
            .remove("refresh_token")
            .apply()
    }

    fun getAccessToken(): String? = prefs.getString("access_token", null)

    fun isLoggedIn(): Boolean = getAccessToken() != null
}
