package com.stiivenortiz.pruebatecnicaredeban.view.core.navigation

import com.stiivenortiz.pruebatecnicaredeban.view.core.model.PaymentInput
import kotlinx.serialization.Serializable

@Serializable
object Dashboard

@Serializable
object PaymentAmount

@Serializable
data class TransactionDetail(
    val transactionId: Long
)

@Serializable
data class PaymentStatus(val paymentInput: PaymentInput)