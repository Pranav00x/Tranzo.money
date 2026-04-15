package com.tranzo.app.ui.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tranzo.app.data.api.TranzoApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.tranzo.app.data.model.*

data class CardInfo(
    val id: String = "",
    val last4: String = "4291",
    val type: String = "virtual",         // virtual | physical
    val status: String = "active",        // active | frozen | pending
    val network: String = "visa",
    val dailyLimit: Double = 1000.0,
    val monthlyLimit: Double = 10000.0,
    val dailySpent: Double = 0.0,
    val monthlySpent: Double = 0.0,
    val expiryMonth: Int = 12,
    val expiryYear: Int = 2028,
)

data class CardTransaction(
    val id: String,
    val merchant: String,
    val category: String,
    val amount: Double,
    val currency: String = "USD",
    val status: String = "completed",
    val timestamp: String,
)

data class CardUiState(
    val isLoading: Boolean = false,
    val hasCard: Boolean = false,
    val card: CardInfo? = null,
    val transactions: List<CardTransaction> = emptyList(),
    val isOrdering: Boolean = false,
    val orderSuccess: Boolean = false,
    val error: String? = null,
)

@HiltViewModel
class CardViewModel @Inject constructor(
    private val api: TranzoApi,
) : ViewModel() {

    private val _state = MutableStateFlow(CardUiState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        loadCard()
        loadTransactions()
    }

    fun loadCard() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val response = api.getCard()
                _state.value = _state.value.copy(
                    isLoading = false,
                    hasCard = true,
                    card = CardInfo(
                        id = response.id,
                        last4 = response.maskedPan.takeLast(4),
                        type = response.type,
                        status = response.status,
                        network = response.network,
                        // Mapping optional limits
                        dailyLimit = (response.spendLimitCents?.toDouble() ?: 0.0) / 100.0,
                        expiryMonth = response.expiry.split("/").firstOrNull()?.toIntOrNull() ?: 12,
                        expiryYear = 2000 + (response.expiry.split("/").lastOrNull()?.toIntOrNull() ?: 28),
                    ),
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    hasCard = false, // If API fails (e.g. 404 No Card), set hasCard to false
                    error = if (e.message?.contains("404") == true) null else e.message,
                )
            }
        }
    }

    fun loadTransactions() {
        viewModelScope.launch {
            try {
                val response = api.getCardTransactions(limit = 10)
                _state.value = _state.value.copy(
                    transactions = response.transactions.map {
                        CardTransaction(
                            id = it.id,
                            merchant = it.merchantName,
                            category = it.merchantCategory ?: "General",
                            amount = it.amountCents.toDouble() / 100.0,
                            currency = it.currency,
                            status = it.status,
                            timestamp = it.createdAt,
                        )
                    }
                )
            } catch (e: Exception) {
                // Silently handle history fetch errors or log them
            }
        }
    }

    fun orderCard(type: String, cardholderName: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isOrdering = true, error = null)
            try {
                api.orderCard(OrderCardRequest(type = type, cardholderName = cardholderName))
                _state.value = _state.value.copy(
                    isOrdering = false,
                    orderSuccess = true,
                )
                loadCard()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isOrdering = false,
                    error = e.message ?: "Failed to order card",
                )
            }
        }
    }

    fun freezeCard() {
        val cardId = _state.value.card?.id ?: return
        viewModelScope.launch {
            try {
                api.setCardFrozen(CardFreezeRequest(frozen = true))
                _state.value = _state.value.copy(
                    card = _state.value.card?.copy(status = "frozen"),
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun unfreezeCard() {
        val cardId = _state.value.card?.id ?: return
        viewModelScope.launch {
            try {
                api.setCardFrozen(CardFreezeRequest(frozen = false))
                _state.value = _state.value.copy(
                    card = _state.value.card?.copy(status = "active"),
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }
}
