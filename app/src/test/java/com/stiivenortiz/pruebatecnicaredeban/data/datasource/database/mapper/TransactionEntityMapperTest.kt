package com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.mapper

import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.entities.TransactionEntity
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionBusinessStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionInternalStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionOperationType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test


class TransactionEntityMapperTest {

    @Test
    fun `toDomain should map entity to domain model correctly`() {
        // GIVEN
        val entity = TransactionEntity(
            id = 10L,
            receiptId = "REC789",
            operationType = TransactionOperationType.SALE.name,
            amount = "15000",
            internalStatus = TransactionInternalStatus.COMPLETED.name,
            businessStatus = TransactionBusinessStatus.APPROVED.name,
            businessStatusDescription = "Approved Transaction",
            maskedCardNumber = "4507 99** **** 0123",
            isVoided = false,
            timestamp = 1715100000000L
        )

        // WHEN
        val domainModel = entity.toDomain()

        // THEN
        assertEquals(entity.id, domainModel.id)
        assertEquals(entity.receiptId, domainModel.receiptId)
        assertEquals(TransactionOperationType.SALE, domainModel.operationType)
        assertEquals(entity.amount, domainModel.amount)
        assertEquals(TransactionInternalStatus.COMPLETED, domainModel.internalStatus)
        assertEquals(TransactionBusinessStatus.APPROVED, domainModel.businessStatus)
        assertEquals(entity.businessStatusDescription, domainModel.businessStatusDescription)
        assertEquals(entity.maskedCardNumber, domainModel.maskedCardNumber)
        assertEquals(entity.isVoided, domainModel.isVoided)
        assertEquals(entity.timestamp, domainModel.timestamp)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `toDomain should throw exception when operationType is invalid`() {
        // GIVEN
        val entity = createMockEntity(operationType = "INVALID_TYPE")

        // WHEN
        entity.toDomain()

        // THEN - Expects IllegalArgumentException
    }

    @Test
    fun `toDomain should handle voided transaction status correctly`() {
        // GIVEN
        val entity =
            createMockEntity(isVoided = true, operationType = TransactionOperationType.VOID.name)

        // WHEN
        val domainModel = entity.toDomain()

        // THEN
        assertTrue(domainModel.isVoided)
        assertEquals(TransactionOperationType.VOID, domainModel.operationType)
    }

    private fun createMockEntity(
        operationType: String = TransactionOperationType.SALE.name,
        isVoided: Boolean = false
    ) = TransactionEntity(
        id = 1L,
        receiptId = "REC123",
        operationType = operationType,
        amount = "1000",
        internalStatus = TransactionInternalStatus.COMPLETED.name,
        businessStatus = TransactionBusinessStatus.APPROVED.name,
        businessStatusDescription = null,
        maskedCardNumber = null,
        isVoided = isVoided,
        timestamp = System.currentTimeMillis()
    )
}