package com.tranzo.app.ui.home

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
            _state.value = _state.value.copy(isLoading = true)
            try {
                // Load user and balances in parallel
                val userDeferred = viewModelScope.launch {
                    try {
                        val user = api.getMe()
                        _state.value = _state.value.copy(user = user)
                    } catch (_: Exception) { }
                }

                val balancesDeferred = viewModelScope.launch {
                    try {
                        val response = api.getBalances()
                        val total = response.balances.sumOf {
                            try {
                                it.formatted.toDouble()
                            } catch (e: Exception) {
                                0.0
                            }
                        }
                        _state.value = _state.value.copy(
                            balances = response.balances,
                            totalUsdBalance = total,
                        )
                    } catch (_: Exception) { }
                }

                userDeferred.join()
                balancesDeferred.join()

                _state.value = _state.value.copy(isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message,
                )
            }
        }
    }

    fun refresh() {
        loadDashboard()
    }
}
