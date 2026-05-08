package com.stiivenortiz.pruebatecnicaredeban.view.paymentstatus

data class PaymentStatusUiState(
    val status: PaymentStatus = PaymentStatus.STARTING,
    val isLoading: Boolean = true,
    val error: String? = null,
    val transactionId: Long? = null
)