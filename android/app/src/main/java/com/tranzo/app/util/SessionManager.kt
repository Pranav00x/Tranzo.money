package com.tranzo.app.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * SessionManager - Handles user session persistence
 * Stores and retrieves user tokens, profile data, and preferences
 */
@Singleton
class SessionManager @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    private val prefs = context.getSharedPreferences("tranzo_session", Context.MODE_PRIVATE)

    // ─────────────────────────────────────────────────────────
    // Token Management
    // ─────────────────────────────────────────────────────────

    fun saveTokens(accessToken: String, refreshToken: String) {
        prefs.edit()
            .putString("access_token", accessToken)
            .putString("refresh_token", refreshToken)
            .putLong("token_timestamp", System.currentTimeMillis())
            .apply()
    }

    fun getAccessToken(): String? = prefs.getString("access_token", null)

    fun getRefreshToken(): String? = prefs.getString("refresh_token", null)

    fun clearTokens() {
        prefs.edit()
            .remove("access_token")
            .remove("refresh_token")
            .remove("token_timestamp")
            .apply()
    }

    fun isTokenValid(): Boolean {
        val token = getAccessToken()
        return token != null && token.isNotEmpty()
    }

    // ─────────────────────────────────────────────────────────
    // User Data Management
    // ─────────────────────────────────────────────────────────

    fun saveUserData(
        userId: String,
        email: String,
        firstName: String? = null,
        lastName: String? = null,
        phone: String? = null,
        avatarUrl: String? = null,
        walletAddress: String? = null,
    ) {
        prefs.edit().apply {
            putString("user_id", userId)
            putString("email", email)
            firstName?.let { putString("first_name", it) }
            lastName?.let { putString("last_name", it) }
            phone?.let { putString("phone", it) }
            avatarUrl?.let { putString("avatar_url", it) }
            walletAddress?.let { putString("wallet_address", it) }
            putLong("user_data_timestamp", System.currentTimeMillis())
            apply()
        }
    }

    fun getUserId(): String? = prefs.getString("user_id", null)

    fun getEmail(): String? = prefs.getString("email", null)

    fun getUserProfile(): UserProfile? {
        val userId = getUserId() ?: return null
        return UserProfile(
            userId = userId,
            email = getEmail() ?: "",
            firstName = prefs.getString("first_name", null),
            lastName = prefs.getString("last_name", null),
            phone = prefs.getString("phone", null),
            avatarUrl = prefs.getString("avatar_url", null),
            walletAddress = prefs.getString("wallet_address", null),
        )
    }

    fun clearUserData() {
        prefs.edit()
            .remove("user_id")
            .remove("email")
            .remove("first_name")
            .remove("last_name")
            .remove("phone")
            .remove("avatar_url")
            .remove("wallet_address")
            .remove("user_data_timestamp")
            .apply()
    }

    // ─────────────────────────────────────────────────────────
    // Session Management
    // ─────────────────────────────────────────────────────────

    fun isLoggedIn(): Boolean {
        return isTokenValid() && getUserId() != null
    }

    fun clearSession() {
        clearTokens()
        clearUserData()
    }

    fun getSessionInfo(): SessionInfo? {
        if (!isLoggedIn()) return null
        return SessionInfo(
            userId = getUserId() ?: return null,
            email = getEmail() ?: return null,
            accessToken = getAccessToken() ?: return null,
            refreshToken = getRefreshToken() ?: return null,
            profile = getUserProfile(),
        )
    }

    // ─────────────────────────────────────────────────────────
    // Helper Models
    // ─────────────────────────────────────────────────────────

    data class UserProfile(
        val userId: String,
        val email: String,
        val firstName: String?,
        val lastName: String?,
        val phone: String?,
        val avatarUrl: String?,
        val walletAddress: String?,
    ) {
        val displayName: String get() = "$firstName $lastName".trim().ifBlank { email }
    }

    data class SessionInfo(
        val userId: String,
        val email: String,
        val accessToken: String,
        val refreshToken: String,
        val profile: UserProfile?,
    )
}
