package com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.dto.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnnulmentResponse(

    @SerialName("statusCode") val statusCode: String,
    @SerialName("statusDescription") val statusDescription: String

)