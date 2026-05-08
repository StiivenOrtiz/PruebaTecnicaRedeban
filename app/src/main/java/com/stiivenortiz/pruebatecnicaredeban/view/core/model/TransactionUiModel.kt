package com.stiivenortiz.pruebatecnicaredeban.view.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class TransactionUiModel(
    val id: Long,
    val amount: String,
    val maskPan: String,
    val type: TransactionUiType,
    val receiptId: String,
    val date: String,
    val status: TransactionUiStatus,
    val isVoided: Boolean
) : Parcelable
