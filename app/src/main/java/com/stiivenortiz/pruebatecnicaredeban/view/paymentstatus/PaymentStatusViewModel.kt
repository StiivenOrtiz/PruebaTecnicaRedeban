package com.stiivenortiz.pruebatecnicaredeban.view.paymentstatus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stiivenortiz.pruebatecnicaredeban.domain.usecase.ProcessTransactionUseCase
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.PaymentInput
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.PaymentStatusProcess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentStatusViewModel @Inject constructor(
    private val processTransactionUseCase: ProcessTransactionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentStatusUiState())
    val uiState: StateFlow<PaymentStatusUiState> = _uiState.asStateFlow()

    fun startTransaction(input: PaymentInput) {
        if (_uiState.value.status != PaymentStatusProcess.STARTING) return

        viewModelScope.launch {
            _uiState.update { it.copy(status = PaymentStatusProcess.PENDING) }

            val result = processTransactionUseCase(
                amount = input.amount,
                transactionId = input.transactionId
            )

            result.fold(
                onSuccess = { paymentProcess ->
                    _uiState.update {
                        it.copy(
                            status = paymentProcess.paymentStatusProcess,
                            transactionId = paymentProcess.transactionId,
                            isLoading = false
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(status = PaymentStatusProcess.FAILED, isLoading = false)
                    }
                }
            )
        }
    }
}