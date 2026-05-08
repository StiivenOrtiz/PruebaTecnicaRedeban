package com.stiivenortiz.pruebatecnicaredeban.view.core.model

import kotlinx.serialization.Serializable

@Serializable
data class TransactionUiModel(
    val id: Long,
    val amount: String,
    val maskPan: String,
    val type: TransactionUiType,
    val receiptId: String,
    val date: String,
    val status: TransactionUiStatus,
    val isVoided: Boolean
)
