package com.stiivenortiz.pruebatecnicaredeban.domain.usecase

import com.stiivenortiz.pruebatecnicaredeban.domain.mapper.toUiPaymentProcess
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.AnnulmentTransactionRepository
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.AuthorizeTransactionRepository
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.PaymentProcess
import javax.inject.Inject

class ProcessTransactionUseCase @Inject constructor(
    private val authRepository: AuthorizeTransactionRepository,
    private val annulRepository: AnnulmentTransactionRepository
) {
    suspend operator fun invoke(
        amount: String? = null,
        transactionId: Long? = null
    ): Result<PaymentProcess> {
        return runCatching {
            when {
                transactionId != null -> {
                    annulRepository.annulTransaction(transactionId)?.toUiPaymentProcess()
                        ?: throw Exception("Original transaction not found")
                }

                amount != null -> {
                    authRepository.authTransaction(amount).toUiPaymentProcess()
                }

                else -> throw IllegalArgumentException("Parameters are missing")
            }
        }
    }
}