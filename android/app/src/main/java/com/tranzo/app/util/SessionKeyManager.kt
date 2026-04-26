package com.tranzo.app.util

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionKeyManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        "tranzo_session_keys",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private const val KEY_CARD_SESSION_PK = "card_session_private_key"
        private const val KEY_CARD_SPEND_LIMIT = "card_spend_limit"
        private const val KEY_CARD_ACTIVE = "card_active_status"
        private const val KEY_SERIALIZED_ACCOUNT = "serialized_kernel_account"
    }

    /**
     * Save a generated session key and its spending limit locally.
     */
    fun saveCardSession(privateKey: String, spendLimitEth: String, serializedAccount: String) {
        prefs.edit().apply {
            putString(KEY_CARD_SESSION_PK, privateKey)
            putString(KEY_CARD_SPEND_LIMIT, spendLimitEth)
            putString(KEY_SERIALIZED_ACCOUNT, serializedAccount)
            putBoolean(KEY_CARD_ACTIVE, true)
            apply()
        }
    }

    fun getCardSessionKey(): String? = prefs.getString(KEY_CARD_SESSION_PK, null)
    fun getCardSpendLimit(): String? = prefs.getString(KEY_CARD_SPEND_LIMIT, "0.1")
    fun getSerializedAccount(): String? = prefs.getString(KEY_SERIALIZED_ACCOUNT, null)
    
    fun isCardActive(): Boolean = prefs.getBoolean(KEY_CARD_ACTIVE, false)

    fun clearCardSession() {
        prefs.edit().clear().apply()
    }
}
