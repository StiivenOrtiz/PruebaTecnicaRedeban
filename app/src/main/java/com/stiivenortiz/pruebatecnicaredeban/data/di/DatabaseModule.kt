package com.stiivenortiz.pruebatecnicaredeban.data.di

import android.content.Context
import androidx.room.Room
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.TransactionsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    const val TRANSACTIONS_DATABASE_NAME = "transactions_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        TransactionsDatabase::class.java,
        TRANSACTIONS_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideTransactionDao(db: TransactionsDatabase) = db.getTransactionDao()

}