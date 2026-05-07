package com.stiivenortiz.pruebatecnicaredeban.domain.repository

interface AnnulmentTransactionRepository {

    suspend fun annulTransaction(transactionId: String)

}