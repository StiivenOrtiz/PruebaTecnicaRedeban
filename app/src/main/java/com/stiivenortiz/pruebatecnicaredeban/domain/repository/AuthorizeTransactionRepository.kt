package com.stiivenortiz.pruebatecnicaredeban.domain.repository

import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionModel

interface AuthorizeTransactionRepository {

    suspend fun authTransaction(amount: String): TransactionModel

}