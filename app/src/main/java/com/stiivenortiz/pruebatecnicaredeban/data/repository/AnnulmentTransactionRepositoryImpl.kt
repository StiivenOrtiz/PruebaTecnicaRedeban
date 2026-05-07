package com.stiivenortiz.pruebatecnicaredeban.data.repository

import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.dao.TransactionDao
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.entities.TransactionEntity
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.mapper.toDomain
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.api.TransactionApiService
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.dto.request.AnnulmentRequest
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionBusinessStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionInternalStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionModel
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionOperationType
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.AnnulmentTransactionRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class AnnulmentTransactionRepositoryImpl @Inject constructor(
    val api: TransactionApiService,
    val dao: TransactionDao,
) : AnnulmentTransactionRepository {

    override suspend fun annulTransaction(transactionId: Long): TransactionModel? {

        val transactionParent = dao.getTransactionById(transactionId) ?: return null

        val newTx = TransactionEntity(
            amount = transactionParent.amount,
            internalStatus = TransactionInternalStatus.PENDING.name,
            operationType = TransactionOperationType.VOID.name,
            businessStatus = TransactionBusinessStatus.PENDING.name,
            receiptId = transactionParent.receiptId,
            maskedCardNumber = transactionParent.maskedCardNumber,
            panFingerprint = transactionParent.panFingerprint,
            parentTransactionId = transactionId,
            timestamp = System.currentTimeMillis()
        )

        val txId = dao.insertTransaction(newTx)

        val annulmentRequest = AnnulmentRequest(
            receiptId = transactionParent.receiptId!!,
            amount = transactionParent.amount
        )

        return runCatching {
            api.annulPayment(annulmentRequest)
        }.fold(
            onSuccess = { response ->
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!
                    val finalEntity = newTx.copy(
                        id = txId,
                        internalStatus = TransactionInternalStatus.COMPLETED.name,
                        businessStatus = if (data.statusCode == "00")
                            TransactionBusinessStatus.APPROVED.name
                        else TransactionBusinessStatus.DECLINED.name,
                        businessStatusCode = data.statusCode,
                        businessStatusDescription = data.statusDescription,
                        timestamp = System.currentTimeMillis()
                    )

                    dao.updateTransaction(finalEntity)
                    dao.updateTransaction(transactionParent.copy(isVoided = true))
                    finalEntity.toDomain()
                } else
                    updateAndReturnError(txId, TransactionInternalStatus.UNKNOWN_ERROR, newTx)
            },
            onFailure = { throwable ->
                if (throwable is CancellationException) throw throwable

                updateAndReturnError(
                    txId,
                    TransactionInternalStatus.NETWORK_ERROR,
                    newTx
                )
            }
        )

    }

    private suspend fun updateAndReturnError(
        id: Long,
        transactionInternalStatus: TransactionInternalStatus,
        transactionEntity: TransactionEntity
    ): TransactionModel {
        val finalEntity = transactionEntity.copy(
            id = id,
            internalStatus = transactionInternalStatus.name,
            businessStatus = TransactionBusinessStatus.DECLINED.name,
            timestamp = System.currentTimeMillis()
        )

        dao.updateTransaction(finalEntity)

        return finalEntity.toDomain()
    }

}