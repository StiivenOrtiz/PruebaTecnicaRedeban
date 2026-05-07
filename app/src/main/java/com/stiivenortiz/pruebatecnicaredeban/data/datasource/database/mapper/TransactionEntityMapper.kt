package com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.mapper

import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.entities.TransactionEntity
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionBusinessStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionInternalStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionModel
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionOperationType

fun TransactionEntity.toDomain(): TransactionModel {

    return TransactionModel(
        id = id,
        receiptId = receiptId,
        operationType = TransactionOperationType.valueOf(operationType),
        amount = amount,
        internalStatus = TransactionInternalStatus.valueOf(internalStatus),
        businessStatus = TransactionBusinessStatus.valueOf(businessStatus),
        businessStatusDescription = businessStatusDescription,
        maskedCardNumber = maskedCardNumber,
        isVoided = isVoided,
        timestamp = timestamp
    )

}