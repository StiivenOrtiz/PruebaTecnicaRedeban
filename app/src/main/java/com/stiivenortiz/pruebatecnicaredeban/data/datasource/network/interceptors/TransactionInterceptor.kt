package com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.interceptors

import com.stiivenortiz.pruebatecnicaredeban.BuildConfig.AUTHORIZATION
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TransactionInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain
                .request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", AUTHORIZATION)
                .build()
        )
    }

}