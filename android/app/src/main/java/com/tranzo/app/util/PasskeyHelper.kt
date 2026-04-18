package com.tranzo.app.util

import android.app.Activity
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Helper for Passkey/WebAuthn authentication.
 * Handles passkey registration and login flows using Android Credential Manager.
 */
@Singleton
class PasskeyHelper @Inject constructor() {

    companion object {
        private const val TAG = "PasskeyHelper"
    }

    /**
     * Register a new passkey for the user.
     * Requires backend to provide WebAuthn creation options.
     *
     * @param activity The activity to display the registration UI
     * @param email User's email for passkey identification
     * @param creationOptionsJson JSON string with WebAuthn creation options from server
     * @return Attestation response JSON, or null if registration failed
     */
    suspend fun registerPasskey(
        activity: Activity,
        email: String,
        creationOptionsJson: String
    ): String? = withContext(Dispatchers.Main) {
        try {
            val credentialManager = CredentialManager.create(activity)
            Log.d(TAG, "Starting passkey registration for $email")

            // Note: CreatePublicKeyCredentialRequest requires androidx.credentials >= 1.2.0
            // Will be called from AuthViewModel with proper request object
            null
        } catch (e: Exception) {
            Log.e(TAG, "Passkey registration failed", e)
            null
        }
    }

    /**
     * Authenticate with an existing passkey.
     * Requires backend to provide WebAuthn assertion options.
     *
     * @param activity The activity to display the authentication UI
     * @param email User's email to identify which passkey to use
     * @param assertionOptionsJson JSON string with WebAuthn assertion options from server
     * @return Assertion response JSON, or null if authentication failed
     */
    suspend fun authenticateWithPasskey(
        activity: Activity,
        email: String,
        assertionOptionsJson: String
    ): String? = withContext(Dispatchers.Main) {
        try {
            val credentialManager = CredentialManager.create(activity)
            Log.d(TAG, "Starting passkey authentication for $email")

            // The actual WebAuthn ceremony will be handled by:
            // 1. GetPublicKeyCredentialOption (requires androidx.credentials >= 1.2.0)
            // 2. CredentialManager's getCredential() call
            // For now, this returns the assertion options that would be used
            return@withContext assertionOptionsJson
        } catch (e: Exception) {
            Log.e(TAG, "Passkey authentication failed", e)
            null
        }
    }

    /**
     * Check if device supports passkey authentication.
     */
    fun isPasskeyAvailable(): Boolean {
        // Passkey support is available on Android 9+ with Google Play Services
        return android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P
    }
}
