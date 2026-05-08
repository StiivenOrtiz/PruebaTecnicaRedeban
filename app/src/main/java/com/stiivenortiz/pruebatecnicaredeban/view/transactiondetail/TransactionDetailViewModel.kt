package com.stiivenortiz.pruebatecnicaredeban.view.transactiondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stiivenortiz.pruebatecnicaredeban.domain.mapper.toUiModel
import com.stiivenortiz.pruebatecnicaredeban.domain.usecase.GetTransactionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class TransactionDetailViewModel @Inject constructor(
    private val getTransactionUseCase: GetTransactionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val transactionId: Long = checkNotNull(savedStateHandle["transactionId"]) {
        "No se encontró el transactionId en los argumentos de navegación"
    }

    val uiState: StateFlow<TransactionDetailUiState> = getTransactionUseCase(transactionId)
        .map { domainModel ->
            if (domainModel != null) {
                TransactionDetailUiState(
                    isLoading = false,
                    transaction = domainModel.toUiModel(),
                    errorMessage = null
                )
            } else {
                TransactionDetailUiState(
                    isLoading = false,
                    transaction = null,
                    errorMessage = "Transacción no encontrada"
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = TransactionDetailUiState(isLoading = true)
        )
}