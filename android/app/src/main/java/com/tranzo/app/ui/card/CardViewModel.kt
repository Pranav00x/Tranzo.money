package com.tranzo.app.ui.card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tranzo.app.data.api.TranzoApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

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
        loadCard()
    }

    fun loadCard() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                // TODO: Replace with actual API call when card endpoints are live
                // val response = api.getCard()
                // For now, use mock data to demonstrate the UI
                _state.value = _state.value.copy(
                    isLoading = false,
                    hasCard = true,
                    card = CardInfo(
                        id = "card_1",
                        last4 = "4291",
                        type = "virtual",
                        status = "active",
                    ),
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message,
                )
            }
        }
    }

    fun orderCard(type: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isOrdering = true, error = null)
            try {
                // TODO: api.orderCard(OrderCardRequest(type))
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
        viewModelScope.launch {
            try {
                // TODO: api.freezeCard(cardId)
                _state.value = _state.value.copy(
                    card = _state.value.card?.copy(status = "frozen"),
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun unfreezeCard() {
        viewModelScope.launch {
            try {
                // TODO: api.unfreezeCard(cardId)
                _state.value = _state.value.copy(
                    card = _state.value.card?.copy(status = "active"),
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }
}
