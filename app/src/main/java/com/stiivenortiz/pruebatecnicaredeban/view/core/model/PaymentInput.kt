package com.stiivenortiz.pruebatecnicaredeban.view.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class PaymentInput(
    val transactionId: Long? = null,
    val amount: String? = null
) : Parcelable