package com.stiivenortiz.pruebatecnicaredeban.domain.model

data class TransactionModel(

    val id: Long,
    val receiptId: String?,
    val operationType: TransactionOperationType,
    val amount: String,
    val internalStatus: TransactionInternalStatus,
    val businessStatus: TransactionBusinessStatus?,
    val businessStatusDescription: String?,
    val isVoided: Boolean,
    val maskedCardNumber: String?,
    val timestamp: Long

)