package com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.entities.TransactionEntity

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): PagingSource<Int, TransactionEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE transaction_id = :id")
    suspend fun deleteTransactionById(id: Long)

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Query("UPDATE transactions SET status = :newStatus, status_code = :newStatusCode, status_description = :newStatusDescription WHERE transaction_id = :id")
    suspend fun updateTransactionStatus(
        id: Long,
        newStatus: String,
        newStatusCode: String,
        newStatusDescription: String?
    )

    @Query("SELECT * FROM transactions WHERE transaction_id = :id LIMIT 1")
    suspend fun getTransactionById(id: Long): TransactionEntity?

}