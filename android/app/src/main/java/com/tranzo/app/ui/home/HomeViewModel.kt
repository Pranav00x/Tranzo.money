package com.tranzo.app.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tranzo.app.data.api.TranzoApi
import com.tranzo.app.data.model.TokenBalance
import com.tranzo.app.data.model.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val user: UserResponse? = null,
    val balances: List<TokenBalance> = emptyList(),
    val totalUsdBalance: Double = 0.0,
    val error: String? = null,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val api: TranzoApi,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        loadDashboard()
    }

    fun loadDashboard() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                // Load user
                try {
                    val user = api.getMe()
                    _state.value = _state.value.copy(user = user)
                    Log.d("HomeViewModel", "Loaded user: ${user.email}")
                } catch (e: Exception) {
                    Log.e("HomeViewModel", "Failed to load user", e)
                    _state.value = _state.value.copy(error = "Failed to load user: ${e.message}")
                }

                // Load balances
                try {
                    val response = api.getBalances()
                    val total = response.balances.sumOf {
                        try {
                            it.formatted.toDoubleOrNull() ?: 0.0
                        } catch (e: Exception) {
                            0.0
                        }
                    }
                    _state.value = _state.value.copy(
                        balances = response.balances,
                        totalUsdBalance = total,
                    )
                    Log.d("HomeViewModel", "Loaded ${response.balances.size} balances")
                } catch (e: Exception) {
                    Log.e("HomeViewModel", "Failed to load balances", e)
                    // Don't fail completely, just show empty balances
                    _state.value = _state.value.copy(balances = emptyList())
                }

                _state.value = _state.value.copy(isLoading = false)
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Unexpected error in loadDashboard", e)
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Unexpected error: ${e.message}",
                )
            }
        }
    }

    fun refresh() {
        loadDashboard()
    }
}
