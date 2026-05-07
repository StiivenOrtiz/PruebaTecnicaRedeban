package com.stiivenortiz.pruebatecnicaredeban.domain.repository

import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionModel

interface AnnulmentTransactionRepository {

    suspend fun annulTransaction(transactionId: Long): TransactionModel?

}