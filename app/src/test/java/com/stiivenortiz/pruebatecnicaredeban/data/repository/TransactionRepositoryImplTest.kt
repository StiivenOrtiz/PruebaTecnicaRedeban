package com.stiivenortiz.pruebatecnicaredeban.data.repository

import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.dao.TransactionDao
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.entities.TransactionEntity
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
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
        val entities = listOf(mockk<TransactionEntity>(relaxed = true))
        every { dao.getAllTransactions() } returns flowOf(entities)

        // WHEN
        val result = repository.getTransactions().first()

        // THEN
        assertEquals(1, result.size)
        verify(exactly = 1) { dao.getAllTransactions() }
    }

    @Test
    fun `getTransaction should return domain model when id exists`() = runTest {
        // GIVEN
        val id = 1L
        val entity = mockk<TransactionEntity>(relaxed = true)
        every { dao.getTransactionByIdFlow(id) } returns flowOf(entity)

        // WHEN
        val result = repository.getTransaction(id).first()

        // THEN
        assertNotNull(result)
        verify(exactly = 1) { dao.getTransactionByIdFlow(id) }
    }
}