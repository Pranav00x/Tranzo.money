package com.tranzo.app.ui.security

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SecurityUiState(
    val isBiometricEnabled: Boolean = false,
    val isTransactionLockEnabled: Boolean = false,
    val isAutoLockEnabled: Boolean = false,
    val pinHash: String? = null,
    val error: String? = null,
)

@HiltViewModel
class SecurityViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
) : ViewModel() {

    private val prefs = context.getSharedPreferences("tranzo_security", Context.MODE_PRIVATE)

    private val _state = MutableStateFlow(
        SecurityUiState(
            isBiometricEnabled = prefs.getBoolean("biometric_enabled", true),
            isTransactionLockEnabled = prefs.getBoolean("transaction_lock", true),
            isAutoLockEnabled = prefs.getBoolean("auto_lock", true),
            pinHash = prefs.getString("pin_hash", null),
        )
    )
    val state = _state.asStateFlow()

    fun setPin(pin: String) {
        viewModelScope.launch {
            // In a real app, use a proper hashing library (e.g., Argon2, Scrypt)
            // For now, we'll use a simple mock hash for demonstration
            val hash = "hash_$pin" 
            prefs.edit().putString("pin_hash", hash).apply()
            _state.value = _state.value.copy(pinHash = hash)
        }
    }

    fun validatePin(pin: String): Boolean {
        val storedHash = prefs.getString("pin_hash", null)
        return storedHash == "hash_$pin"
    }

    fun setBiometricEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("biometric_enabled", enabled).apply()
        _state.value = _state.value.copy(isBiometricEnabled = enabled)
    }

    fun setTransactionLockEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("transaction_lock", enabled).apply()
        _state.value = _state.value.copy(isTransactionLockEnabled = enabled)
    }

    fun setAutoLockEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("auto_lock", enabled).apply()
        _state.value = _state.value.copy(isAutoLockEnabled = enabled)
    }
}
