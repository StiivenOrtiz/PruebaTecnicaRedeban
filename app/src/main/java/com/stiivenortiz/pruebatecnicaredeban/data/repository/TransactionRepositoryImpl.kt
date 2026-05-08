package com.stiivenortiz.pruebatecnicaredeban.data.repository

import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.dao.TransactionDao
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.mapper.toDomain
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionModel
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    val dao: TransactionDao,
) : TransactionRepository {

    override fun getTransactions(): Flow<List<TransactionModel>> {
        return dao.getAllTransactions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getTransaction(id: Long): Flow<TransactionModel> {
        return dao.getTransactionByIdFlow(id).map { it?.toDomain() as TransactionModel }
    }

}