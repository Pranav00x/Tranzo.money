package com.tranzo.app.ui.dripper

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tranzo.app.data.api.TranzoApi
import com.tranzo.app.data.model.CreateStreamRequest
import com.tranzo.app.data.model.StreamDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DripperUiState(
    val isLoading: Boolean = false,
    val streams: List<StreamDetail> = emptyList(),
    val activeCount: Int = 0,
    val error: String? = null,
)

@HiltViewModel
class DripperViewModel @Inject constructor(
    private val api: TranzoApi,
) : ViewModel() {

    private val _state = MutableStateFlow(DripperUiState())
    val state = _state.asStateFlow()

    init {
        loadStreams()
    }

    fun loadStreams() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                val employeeStreams = api.listStreams("employee")
                val employerStreams = api.listStreams("employer")
                val allStreams = (employeeStreams.streams + employerStreams.streams)
                    .distinctBy { it.id }

                _state.value = _state.value.copy(
                    isLoading = false,
                    streams = allStreams,
                    activeCount = allStreams.count { it.status == "ACTIVE" },
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message,
                )
            }
        }
    }

    fun createStream(
        recipientAddress: String,
        tokenAddress: String,
        totalAmount: String,
        durationDays: Int,
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val durationSeconds = durationDays.toLong() * 86400
                val amountPerSecond = (totalAmount.toBigDecimal()
                    .divide(durationSeconds.toBigDecimal(), 18, java.math.RoundingMode.FLOOR))
                    .toPlainString()

                val startTime = java.time.Instant.now().toString()
                val endTime = java.time.Instant.now()
                    .plusSeconds(durationSeconds).toString()

                api.createStream(
                    CreateStreamRequest(
                        employeeAddress = recipientAddress,
                        tokenAddress = tokenAddress,
                        amountPerSecond = amountPerSecond,
                        startTime = startTime,
                        endTime = endTime,
                    )
                )

                _state.value = _state.value.copy(isLoading = false)
                loadStreams() // Refresh
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to create stream",
                )
            }
        }
    }

    fun withdrawFromStream(streamId: String) {
        viewModelScope.launch {
            try {
                api.withdrawFromStream(streamId)
                loadStreams()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }

    fun cancelStream(streamId: String) {
        viewModelScope.launch {
            try {
                api.cancelStream(streamId)
                loadStreams()
            } catch (e: Exception) {
                _state.value = _state.value.copy(error = e.message)
            }
        }
    }
}
