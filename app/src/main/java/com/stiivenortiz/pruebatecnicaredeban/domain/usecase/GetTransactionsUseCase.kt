package com.stiivenortiz.pruebatecnicaredeban.domain.usecase

import com.stiivenortiz.pruebatecnicaredeban.domain.mapper.toUiModel
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.TransactionRepository
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<List<TransactionUiModel>> {
        return repository.getTransactions().map { it -> it.map { it.toUiModel() } }
    }
}