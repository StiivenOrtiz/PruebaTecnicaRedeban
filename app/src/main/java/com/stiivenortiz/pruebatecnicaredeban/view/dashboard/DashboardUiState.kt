package com.stiivenortiz.pruebatecnicaredeban.view.dashboard


import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiModel

data class DashboardUiState(

    val isLoading: Boolean = false,
    val transactions: List<TransactionUiModel> = emptyList(),
    val totalAmount: String = "$0",
    val errorMessage: String? = null

)