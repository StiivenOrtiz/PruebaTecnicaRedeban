package com.stiivenortiz.pruebatecnicaredeban.view.core.mapper

import androidx.annotation.StringRes
import com.stiivenortiz.pruebatecnicaredeban.R
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiStatus

@StringRes
fun TransactionUiStatus.toStringRes(): Int {
    return when (this) {
        TransactionUiStatus.APPROVED -> R.string.transaction_status_approved
        TransactionUiStatus.DECLINED -> R.string.transaction_status_declined
        TransactionUiStatus.PENDING -> R.string.transaction_status_pending
    }
}