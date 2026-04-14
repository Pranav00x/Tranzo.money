package com.tranzo.app.ui.send

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tranzo.app.data.api.TranzoApi
import com.tranzo.app.data.model.SendTokenRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SendUiState(
    val isLoading: Boolean = false,
    val isSent: Boolean = false,
    val txHash: String? = null,
    val intentId: String? = null,
    val error: String? = null,
)

@HiltViewModel
class SendViewModel @Inject constructor(
    private val api: TranzoApi,
) : ViewModel() {

    private val _state = MutableStateFlow(SendUiState())
    val state = _state.asStateFlow()

    // Well-known token addresses on Polygon
    private val tokenAddresses = mapOf(
        "USDC" to "0x3c499c542cEF5E3811e1192ce70d8cC03d5c3359",
        "USDT" to "0xc2132D05D31c914a87C6611C10748AEb04B58e8F",
        "WETH" to "0x7ceB23fD6bC0adD59E62ac25578270cFf1b9f619",
        "POL" to "native",
    )

    fun sendToken(to: String, tokenSymbol: String, amount: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val tokenAddress = tokenAddresses[tokenSymbol]
                    ?: throw IllegalArgumentException("Unknown token: $tokenSymbol")

                val response = api.sendToken(
                    SendTokenRequest(
                        to = to,
                        tokenAddress = tokenAddress,
                        amount = amount,
                    )
                )

                _state.value = _state.value.copy(
                    isLoading = false,
                    isSent = true,
                    txHash = response.txHash,
                    intentId = response.intentId,
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Transfer failed",
                )
            }
        }
    }

    fun reset() {
        _state.value = SendUiState()
    }
}
