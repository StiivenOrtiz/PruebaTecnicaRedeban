package com.stiivenortiz.pruebatecnicaredeban.data.di

import com.stiivenortiz.pruebatecnicaredeban.BuildConfig.BASE_URL
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.api.TransactionApiService
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.interceptors.TransactionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(json: Json, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(ScalarsConverterFactory.create()) // for text/plain
            .addConverterFactory(json.asConverterFactory("application/json; charset=UFT8".toMediaType())) // for json
            .build()
    }

    @Provides
    fun provideJson(): Json {
        return Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        transactionInterceptor: TransactionInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(transactionInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideTransactionApiService(retrofit: Retrofit): TransactionApiService {
        return retrofit.create(TransactionApiService::class.java)
    }

}