package com.tranzo.app.util

import android.app.Activity
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CreatePublicKeyCredentialRequest
import androidx.credentials.CreatePublicKeyCredentialResponse
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPublicKeyCredentialOption
import androidx.credentials.PublicKeyCredential
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Helper for Passkey/WebAuthn authentication.
 * Handles passkey registration and login flows.
 */
@Singleton
class PasskeyHelper @Inject constructor() {

    companion object {
        private const val TAG = "PasskeyHelper"
    }

    /**
     * Register a new passkey for the user.
     *
     * @param activity The activity to display the registration UI
     * @param email User's email for passkey identification
     * @param creationOptions JSON string with WebAuthn creation options from server
     * @return Attestation object as JSON string, or null if registration failed
     */
    suspend fun registerPasskey(
        activity: Activity,
        email: String,
        creationOptions: String
    ): String? = withContext(Dispatchers.Main) {
        try {
            val credentialManager = CredentialManager.create(activity)

            val request = CreatePublicKeyCredentialRequest(
                requestJson = creationOptions
            )

            val result = credentialManager.createCredential(
                activity,
                request
            )

            if (result is CreatePublicKeyCredentialResponse) {
                val registrationResponse = result.registrationResponseJson
                Log.d(TAG, "Passkey registration successful for $email")
                return@withContext registrationResponse
            }
            null
        } catch (e: Exception) {
            Log.e(TAG, "Passkey registration failed", e)
            null
        }
    }

    /**
     * Authenticate with an existing passkey.
     *
     * @param activity The activity to display the authentication UI
     * @param email User's email to identify which passkey to use
     * @param assertionOptions JSON string with WebAuthn assertion options from server
     * @return Assertion object as JSON string, or null if authentication failed
     */
    suspend fun authenticateWithPasskey(
        activity: Activity,
        email: String,
        assertionOptions: String
    ): String? = withContext(Dispatchers.Main) {
        try {
            val credentialManager = CredentialManager.create(activity)

            val getPublicKeyCredentialOption = GetPublicKeyCredentialOption(
                requestJson = assertionOptions
            )

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(getPublicKeyCredentialOption)
                .build()

            val result = credentialManager.getCredential(
                activity,
                request
            )

            val credential = result.credential
            if (credential is PublicKeyCredential) {
                val authenticationResponse = credential.authenticationResponseJson
                Log.d(TAG, "Passkey authentication successful for $email")
                return@withContext authenticationResponse
            }

            null
        } catch (e: Exception) {
            Log.e(TAG, "Passkey authentication failed", e)
            null
        }
    }

    /**
     * Check if device supports passkey authentication.
     */
    fun isPasskeyAvailable(): Boolean {
        // Passkey support is available on Android with CredentialManager
        // Actual availability depends on device and Google Play Services
        return true
    }
}
