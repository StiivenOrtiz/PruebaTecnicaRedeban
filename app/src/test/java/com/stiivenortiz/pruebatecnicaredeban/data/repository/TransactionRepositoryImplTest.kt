package com.stiivenortiz.pruebatecnicaredeban.data.repository

import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.dao.TransactionDao
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.entities.TransactionEntity
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionBusinessStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionInternalStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionOperationType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test


class TransactionRepositoryImplTest {

    private val dao: TransactionDao = mockk()
    private lateinit var repository: TransactionRepositoryImpl

    @Before
    fun setup() {
        repository = TransactionRepositoryImpl(dao)
    }

    @Test
    fun `getTransactions should emit domain models from dao entities`() = runTest {
        // GIVEN
        val entity = createFakeEntity(id = 1L)
        val entities = listOf(entity)
        every { dao.getAllTransactions() } returns flowOf(entities)

        // WHEN
        val result = repository.getTransactions().first()

        // THEN
        assertEquals(1, result.size)
        assertEquals(entity.id, result[0].id)
        verify(exactly = 1) { dao.getAllTransactions() }
    }

    @Test
    fun `getTransaction should return domain model when id exists`() = runTest {
        // GIVEN
        val id = 1L
        val entity = createFakeEntity(id = id)
        every { dao.getTransactionByIdFlow(id) } returns flowOf(entity)

        // WHEN
        val result = repository.getTransaction(id).first()

        // THEN
        assertNotNull(result)
        assertEquals(id, result?.id)
        verify(exactly = 1) { dao.getTransactionByIdFlow(id) }
    }

    private fun createFakeEntity(id: Long) = TransactionEntity(
        id = id,
        receiptId = "123-456",
        operationType = TransactionOperationType.SALE.name,
        amount = "50000",
        internalStatus = TransactionInternalStatus.COMPLETED.name,
        businessStatus = TransactionBusinessStatus.APPROVED.name,
        businessStatusDescription = "Aprobada",
        maskedCardNumber = "4567 56** **** 1234",
        isVoided = false,
        timestamp = System.currentTimeMillis()
    )
}