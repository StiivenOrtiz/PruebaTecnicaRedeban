package com.stiivenortiz.pruebatecnicaredeban.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.dao.TransactionDao
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.entities.TransactionEntity

@Database(entities = [TransactionEntity::class], version = 1)
abstract class TransactionsDatabase : RoomDatabase() {

    abstract fun getTransactionDao(): TransactionDao

}