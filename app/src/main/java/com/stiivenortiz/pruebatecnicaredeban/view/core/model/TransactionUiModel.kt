package com.stiivenortiz.pruebatecnicaredeban.view.core.model

data class TransactionUiModel(
    val id: Long,
    val amount: String,
    val maskPan: String,
    val type: String,
    val receiptId: String,
    val date: String,
    val status: TransactionUiStatus,
    val isVoided: Boolean
)
