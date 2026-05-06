package com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnnulmentRequest(

    @SerialName("receiptId") val receiptId: String,
    @SerialName("amount") val amount: String

)