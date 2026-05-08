package com.stiivenortiz.pruebatecnicaredeban.view.core.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@ConsistentCopyVisibility
@Parcelize
@Serializable
data class PaymentInput private constructor(
    val transactionId: Long?,
    val amount: String?
) : Parcelable {

    companion object {
        fun create(
            transactionId: Long? = null,
            amount: String? = null
        ): PaymentInput {
            require(transactionId != null || amount != null) {
                "Either transactionId or amount must be provided"
            }
            return PaymentInput(transactionId, amount)
        }
    }
}