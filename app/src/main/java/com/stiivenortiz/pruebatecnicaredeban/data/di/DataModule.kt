package com.stiivenortiz.pruebatecnicaredeban.data.di

import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.dao.TransactionDao
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.api.TransactionApiService
import com.stiivenortiz.pruebatecnicaredeban.data.repository.AnnulmentTransactionRepositoryImpl
import com.stiivenortiz.pruebatecnicaredeban.data.repository.AuthorizeTransactionRepositoryImpl
import com.stiivenortiz.pruebatecnicaredeban.data.repository.TransactionRepositoryImpl
import com.stiivenortiz.pruebatecnicaredeban.data.security.SecurityProcessor
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.AnnulmentTransactionRepository
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.AuthorizeTransactionRepository
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideAuthorizeTransactionRepository(
        transactionApiService: TransactionApiService,
        transactionDao: TransactionDao,
        securityProcessor: SecurityProcessor
    ): AuthorizeTransactionRepository {
        return AuthorizeTransactionRepositoryImpl(
            api = transactionApiService,
            dao = transactionDao,
            securityProcessor = securityProcessor
        )
    }

    @Provides
    fun provideAnnulmentTransactionRepository(
        transactionApiService: TransactionApiService,
        transactionDao: TransactionDao,
    ): AnnulmentTransactionRepository {
        return AnnulmentTransactionRepositoryImpl(
            api = transactionApiService,
            dao = transactionDao
        )
    }

    @Provides
    fun provideTransactionRepository(
        transactionDao: TransactionDao,
    ): TransactionRepository {
        return TransactionRepositoryImpl(dao = transactionDao)
    }

}