package com.stiivenortiz.pruebatecnicaredeban.view.paymentstatus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.PaymentInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentStatusViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentStatusUiState())
    val uiState: StateFlow<PaymentStatusUiState> = _uiState

    private var hasStarted = false

    fun startTransaction(input: PaymentInput) {
        if (hasStarted) return
        hasStarted = true

        viewModelScope.launch {

            try {
                _uiState.update {
                    it.copy(
                        status = PaymentStatus.STARTING,
                        isLoading = true,
                        error = null
                    )
                }

                // Simula preparación
                delay(3000)

                _uiState.update {
                    it.copy(status = PaymentStatus.PENDING)
                }

                // Simula espera datáfono
                delay(4000)

                val finalStatus = listOf(
                    PaymentStatus.APPROVED,
                    PaymentStatus.DECLINED,
                    PaymentStatus.FAILED
                ).random()

                _uiState.update {
                    it.copy(
                        status = finalStatus,
                        isLoading = false
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        status = PaymentStatus.FAILED,
                        isLoading = false,
                        error = e.message
                    )
                }
            }
        }
    }

    fun finish(onFinished: (Long) -> Unit, transactionId: Long?) {
        onFinished(transactionId ?: -1L)
    }
}