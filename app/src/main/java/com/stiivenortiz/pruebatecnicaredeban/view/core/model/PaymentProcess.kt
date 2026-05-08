package com.stiivenortiz.pruebatecnicaredeban.view.core.model

data class PaymentProcess (
    val transactionId: Long,
    val paymentStatusProcess: PaymentStatusProcess,
    val statusDescription: String? = null,
)