package com.stiivenortiz.pruebatecnicaredeban.domain.usecase

import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionModel
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.AnnulmentTransactionRepository
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.AuthorizeTransactionRepository
import javax.inject.Inject

class ProcessTransactionUseCase @Inject constructor(
    private val authRepository: AuthorizeTransactionRepository,
    private val annulRepository: AnnulmentTransactionRepository
) {
    suspend operator fun invoke(
        amount: String? = null,
        transactionId: Long? = null
    ): Result<TransactionModel> {
        return runCatching {
            when {
                transactionId != null -> {
                    annulRepository.annulTransaction(transactionId)
                        ?: throw Exception("No se encontró la transacción original")
                }

                amount != null -> {
                    authRepository.authTransaction(amount)
                }

                else -> throw IllegalArgumentException("Faltan parámetros para procesar")
            }
        }
    }
}