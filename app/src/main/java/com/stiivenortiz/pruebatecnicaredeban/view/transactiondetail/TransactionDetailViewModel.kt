package com.stiivenortiz.pruebatecnicaredeban.view.transactiondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiModel
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiStatus
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionDetailViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionDetailUiState())
    val uiState: StateFlow<TransactionDetailUiState> = _uiState.asStateFlow()

    init {
        loadTransaction()
    }

    private fun loadTransaction() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(isLoading = true)
            }

            delay(2000)

            val mockTransaction = TransactionUiModel(
                id = 123456,
                amount = "$450.000",
                maskPan = "1234 56** **** 7890",
                type = TransactionUiType.SALE,
                receiptId = "98765432",
                date = "8:27 am | 7 mayo, 2026",
                status = TransactionUiStatus.APPROVED,
                isVoided = false
            )

            _uiState.update {
                it.copy(
                    isLoading = false,
                    transaction = mockTransaction
                )
            }
        }
    }
}