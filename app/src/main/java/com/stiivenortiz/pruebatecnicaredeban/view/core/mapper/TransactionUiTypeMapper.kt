package com.stiivenortiz.pruebatecnicaredeban.view.core.mapper

import androidx.annotation.StringRes
import com.stiivenortiz.pruebatecnicaredeban.R
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiType

@StringRes
fun TransactionUiType.toStringRes(): Int {
    return when (this) {
        TransactionUiType.SALE -> R.string.transaction_type_sale
        TransactionUiType.VOID -> R.string.transaction_type_void
    }
}