package com.stiivenortiz.pruebatecnicaredeban.view.transactiondetail

import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiModel

data class TransactionDetailUiState(
    val isLoading: Boolean = false,
    val transaction: TransactionUiModel? = null,
    val errorMessage: String? = null
)