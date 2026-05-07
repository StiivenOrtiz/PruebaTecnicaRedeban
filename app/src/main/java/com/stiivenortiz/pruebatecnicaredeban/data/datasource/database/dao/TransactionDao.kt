package com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): PagingSource<Int, TransactionEntity>

    @Query("SELECT * FROM transactions WHERE parent_transaction_id = :saleId AND operation_type = 'VOID' ORDER BY timestamp ASC")
    fun getVoidAttemptsForTransaction(saleId: Long): Flow<List<TransactionEntity>>

    @Query(
        """
        SELECT EXISTS(
            SELECT 1 FROM transactions 
            WHERE parent_transaction_id = :saleId 
            AND operation_type = 'VOID' 
            AND business_status = 'APPROVED'
        )
    """
    )
    fun observeIsEffectivelyVoided(saleId: Long): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity): Long

    @Delete
    suspend fun deleteTransaction(transaction: TransactionEntity)

    @Query("DELETE FROM transactions WHERE transaction_id = :id")
    suspend fun deleteTransactionById(id: Long)

    @Update
    suspend fun updateTransaction(transaction: TransactionEntity)

    @Query(
        """
        UPDATE transactions 
        SET internal_status = :internalStatus, 
            business_status = :businessStatus, 
            business_status_code = :businessStatusCode, 
            business_status_description = :description 
        WHERE transaction_id = :id
    """
    )
    suspend fun updateTransactionStatus(
        id: Long,
        internalStatus: String,
        businessStatus: String?,
        businessStatusCode: String?,
        description: String?
    )

    @Query("SELECT * FROM transactions WHERE transaction_id = :id LIMIT 1")
    suspend fun getTransactionById(id: Long): TransactionEntity?
}