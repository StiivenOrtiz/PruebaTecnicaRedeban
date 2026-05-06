package com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.api

import com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.dto.request.AnnulmentRequest
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.dto.request.AuthorizationRequest
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.dto.response.AnnulmentResponse
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.dto.response.AuthorizationResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TransactionApiService {

    @POST("api/payments/authorization")
    suspend fun authorizePayment(
        @Body request: AuthorizationRequest
    ): Response<AuthorizationResponse>

    @POST("api/payments/annulment")
    suspend fun annulPayment(
        @Body request: AnnulmentRequest
    ): Response<AnnulmentResponse>

}