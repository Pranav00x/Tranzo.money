package com.tranzo.app.ui.swap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tranzo.app.data.api.TranzoApi
import com.tranzo.app.data.model.SwapExecuteRequest
import com.tranzo.app.data.model.SwapQuoteRequest
import com.tranzo.app.data.model.SwapQuoteResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SwapUiState(
    val isLoadingQuote: Boolean = false,
    val isExecuting: Boolean = false,
    val isSwapped: Boolean = false,

    // Quote data
    val fromToken: String = "USDC",
    val toToken: String = "POL",
    val fromAmount: String = "",
    val quote: SwapQuoteResponse? = null,

    // Tx result
    val txHash: String? = null,
    val intentId: String? = null,

    val error: String? = null,
)

@HiltViewModel
class SwapViewModel @Inject constructor(
    private val api: TranzoApi,
) : ViewModel() {

    private val _state = MutableStateFlow(SwapUiState())
    val state = _state.asStateFlow()

    // Well-known token addresses on Base Sepolia
    private val tokenAddresses = mapOf(
        "USDC" to "0x036CbD53842c5426634e7929541eC2318f3dCF7e",
        "USDT" to "0x0000000000000000000000000000000000000000",
        "ETH"  to "0x0000000000000000000000000000000000000000",
    )

    fun onFromTokenChanged(token: String) {
        _state.value = _state.value.copy(fromToken = token, quote = null)
        maybeRefreshQuote()
    }

    fun onToTokenChanged(token: String) {
        _state.value = _state.value.copy(toToken = token, quote = null)
        maybeRefreshQuote()
    }

    fun onFromAmountChanged(amount: String) {
        _state.value = _state.value.copy(fromAmount = amount, quote = null)
        maybeRefreshQuote()
    }

    fun swapDirection() {
        val current = _state.value
        _state.value = current.copy(
            fromToken = current.toToken,
            toToken = current.fromToken,
            quote = null,
        )
        maybeRefreshQuote()
    }

    private fun maybeRefreshQuote() {
        val s = _state.value
        if (s.fromAmount.isBlank()) return
        val amount = s.fromAmount.toDoubleOrNull() ?: return
        if (amount <= 0.0) return
        fetchQuote()
    }

    fun fetchQuote() {
        val s = _state.value
        val fromAddress = tokenAddresses[s.fromToken] ?: return
        val toAddress = tokenAddresses[s.toToken] ?: return

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoadingQuote = true, error = null)
            try {
                val quote = api.getSwapQuote(
                    SwapQuoteRequest(
                        fromToken = fromAddress,
                        toToken = toAddress,
                        amount = s.fromAmount,
                    ),
                )
                _state.value = _state.value.copy(
                    isLoadingQuote = false,
                    quote = quote,
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoadingQuote = false,
                    error = e.message ?: "Failed to get quote",
                )
            }
        }
    }

    fun executeSwap() {
        val s = _state.value
        val quote = s.quote ?: return
        val fromAddress = tokenAddresses[s.fromToken] ?: return
        val toAddress = tokenAddresses[s.toToken] ?: return

        viewModelScope.launch {
            _state.value = _state.value.copy(isExecuting = true, error = null)
            try {
                val result = api.executeSwap(
                    SwapExecuteRequest(
                        fromToken = fromAddress,
                        toToken = toAddress,
                        amount = s.fromAmount,
                        minAmountOut = quote.minAmountOut,
                        slippageBps = quote.slippageBps,
                    ),
                )
                _state.value = _state.value.copy(
                    isExecuting = false,
                    isSwapped = true,
                    txHash = result.txHash,
                    intentId = result.intentId,
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isExecuting = false,
                    error = e.message ?: "Swap failed",
                )
            }
        }
    }

    fun reset() {
        _state.value = SwapUiState()
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}
