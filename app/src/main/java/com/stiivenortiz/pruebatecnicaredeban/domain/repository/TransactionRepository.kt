package com.stiivenortiz.pruebatecnicaredeban.domain.repository

import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionModel
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactions() : Flow<List<TransactionModel>>

    fun getTransaction(id: Long) : Flow<TransactionModel?>
}