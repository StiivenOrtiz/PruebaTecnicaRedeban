package com.stiivenortiz.pruebatecnicaredeban.domain.mapper

import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionBusinessStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionModel
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionOperationType
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiModel
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiStatus
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun TransactionModel.toUiModel(): TransactionUiModel {
    val dateFormatter = SimpleDateFormat("HH:mm | dd MMM, yyyy", Locale.getDefault())

    val parsedAmount = amount.toLongOrNull() ?: 0L
    val isNegative = parsedAmount < 0
    val absAmount = kotlin.math.abs(parsedAmount)

    val formattedAmount = try {
        val formatted = "%,d".format(absAmount).replace(",", ".")
        if (isNegative) "-$$formatted" else "$$formatted"
    } catch (e: Exception) {
        "$ $amount"
    }

    return TransactionUiModel(
        id = id,
        amount = formattedAmount,
        maskPan = maskedCardNumber ?: "",
        type = when (operationType) {
            TransactionOperationType.SALE -> TransactionUiType.SALE
            TransactionOperationType.VOID -> TransactionUiType.VOID
        },
        receiptId = receiptId ?: "",
        date = dateFormatter.format(Date(timestamp)).replaceFirstChar { it.uppercase() },
        status = when (businessStatus) {
            TransactionBusinessStatus.APPROVED -> TransactionUiStatus.APPROVED
            TransactionBusinessStatus.DECLINED -> TransactionUiStatus.DECLINED
            else -> TransactionUiStatus.PENDING
        },
        isVoided = isVoided
    )
}