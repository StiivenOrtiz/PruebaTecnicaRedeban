package com.stiivenortiz.pruebatecnicaredeban.view.paymentstatus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionBusinessStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.usecase.ProcessTransactionUseCase
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.PaymentInput
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
        if (_uiState.value.status != PaymentStatus.STARTING) return

        viewModelScope.launch {
            _uiState.update { it.copy(status = PaymentStatus.PENDING) }

            val result = processTransactionUseCase(
                amount = input.amount,
                transactionId = input.transactionId
            )

            result.fold(
                onSuccess = { transaction ->
                    _uiState.update {
                        it.copy(
                            status = if (transaction.businessStatus == TransactionBusinessStatus.APPROVED)
                                PaymentStatus.APPROVED else PaymentStatus.DECLINED,
                            transactionId = transaction.id,
                            isLoading = false
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(status = PaymentStatus.FAILED, isLoading = false)
                    }
                }
            )
        }
    }
}