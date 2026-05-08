package com.stiivenortiz.pruebatecnicaredeban.view.paymentstatus

import com.stiivenortiz.pruebatecnicaredeban.view.core.model.PaymentStatusProcess

data class PaymentStatusUiState(
    val status: PaymentStatusProcess = PaymentStatusProcess.STARTING,
    val businessStatusDescription: String? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val transactionId: Long? = null
)