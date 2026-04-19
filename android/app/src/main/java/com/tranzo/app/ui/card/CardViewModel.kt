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

/**
 * Card representation for the UI — maps from the raw CardResponse.
 */
data class CardInfo(
    val id: String = "",
    val maskedPan: String = "**** **** **** ----",
    val cardholderName: String = "",
    val expiry: String = "--/--",
    val type: String = "virtual",         // virtual | physical
    val status: String = "pending",       // active | frozen | pending
    val network: String = "Visa",
    val spendLimitCents: Long? = null,
)

/**
 * Card transaction for UI display.
 */
data class CardTransactionUi(
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
    val transactions: List<CardTransactionUi> = emptyList(),
    val isOrdering: Boolean = false,
    val orderSuccess: Boolean = false,
    val user: UserResponse? = null,
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
        loadUser()
        loadCard()
        loadTransactions()
    }

    fun loadUser() {
        viewModelScope.launch {
            try {
                val user = api.getMe()
                _state.value = _state.value.copy(user = user)
            } catch (_: Exception) {}
        }
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
                        maskedPan = response.maskedPan,
                        cardholderName = response.cardholderName,
                        expiry = response.expiry,
                        type = response.type,
                        status = response.status,
                        network = response.network,
                        spendLimitCents = response.spendLimitCents,
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
                        CardTransactionUi(
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

    fun toggleFreeze() {
        val card = _state.value.card ?: return
        if (card.status == "frozen") unfreezeCard() else freezeCard()
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

    private fun freezeCard() {
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

    private fun unfreezeCard() {
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
