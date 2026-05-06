package com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.dto.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorizationRequest(

    @SerialName("amount") val amount: String

)