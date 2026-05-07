package com.stiivenortiz.pruebatecnicaredeban.data.repository

import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.dao.TransactionDao
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.entities.TransactionEntity
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.mapper.toDomain
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.api.TransactionApiService
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.dto.request.AuthorizationRequest
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.parser.HexParser
import com.stiivenortiz.pruebatecnicaredeban.data.security.SecurityProcessor
import com.stiivenortiz.pruebatecnicaredeban.data.utils.PanUtils
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionBusinessStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionInternalStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionModel
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionOperationType
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.AuthorizeTransactionRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class AuthorizeTransactionRepositoryImpl @Inject constructor(
    val api: TransactionApiService,
    val dao: TransactionDao,
    val securityProcessor: SecurityProcessor
) : AuthorizeTransactionRepository {

    override suspend fun authTransaction(amount: String): TransactionModel {

        val newTx = TransactionEntity(
            amount = amount,
            internalStatus = TransactionInternalStatus.PENDING.name,
            operationType = TransactionOperationType.SALE.name,
            businessStatus = TransactionBusinessStatus.PENDING.name,
            timestamp = System.currentTimeMillis()
        )

        val txId = dao.insertTransaction(newTx)
        val authorizationRequest = AuthorizationRequest(amount = amount)

        return kotlin.runCatching {
            api.authorizePayment(authorizationRequest)
        }.fold(
            onSuccess = { response ->
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()!!
                    val pan = PanUtils.extractPan(HexParser.hexToAscii(data.hexData))
                    val finalEntity = newTx.copy(
                        id = txId,
                        internalStatus = TransactionInternalStatus.COMPLETED.name,
                        receiptId = data.receiptId,
                        businessStatus = if (data.statusCode == "00")
                            TransactionBusinessStatus.APPROVED.name
                        else TransactionBusinessStatus.DECLINED.name,
                        businessStatusCode = data.statusCode,
                        businessStatusDescription = data.statusDescription,
                        maskedCardNumber = PanUtils.maskPan(pan),
                        panFingerprint = securityProcessor.generateHash(pan),
                        timestamp = System.currentTimeMillis()
                    )

                    dao.updateTransaction(finalEntity)
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