package com.tranzo.app.util

import android.app.Activity
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Helper for Google Sign-In using Credential Manager (modern, safer approach).
 * Handles OAuth 2.0 token flow and returns ID token for backend verification.
 */
@Singleton
class GoogleSignInHelper @Inject constructor() {

    companion object {
        private const val TAG = "GoogleSignInHelper"
        // Replace with your actual Google Cloud Console Web Client ID
        // You can get this from Google Cloud Console > APIs & Services > Credentials
        private const val GOOGLE_CLIENT_ID = "YOUR_GOOGLE_CLIENT_ID.apps.googleusercontent.com"
    }

    /**
     * Initiate Google Sign-In flow and return ID token.
     *
     * @param activity The activity to display the sign-in UI
     * @return ID token from Google, or null if sign-in failed/cancelled
     */
    suspend fun signIn(activity: Activity): String? = withContext(Dispatchers.Main) {
        try {
            val credentialManager = CredentialManager.create(activity)

            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(GOOGLE_CLIENT_ID)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(activity, request)
            val credential = result.credential

            when (credential) {
                is CustomCredential -> {
                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        val idToken = googleIdTokenCredential.idToken
                        Log.d(TAG, "Sign-in successful. User: ${googleIdTokenCredential.displayName}")
                        return@withContext idToken
                    }
                }
            }
            null
        } catch (e: Exception) {
            Log.e(TAG, "Google sign-in failed", e)
            null
        }
    }
}
