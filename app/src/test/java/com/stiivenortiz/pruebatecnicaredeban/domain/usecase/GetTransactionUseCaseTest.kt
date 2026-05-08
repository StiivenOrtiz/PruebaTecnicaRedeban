package com.stiivenortiz.pruebatecnicaredeban.domain.usecase

import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionModel
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.TransactionRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetTransactionUseCaseTest {
    private val repository: TransactionRepository = mockk()
    private lateinit var getTransactionUseCase: GetTransactionUseCase

    @Before
    fun setup() {
        getTransactionUseCase = GetTransactionUseCase(repository)
    }

    @Test
    fun `when id is provided, should return flow with transaction`() = runTest {
        // GIVEN
        val txId = 1L
        val mockTransaction = mockk<TransactionModel>(relaxed = true)
        every { repository.getTransaction(txId) } returns flowOf(mockTransaction)

        // WHEN
        val result = getTransactionUseCase(txId).first()

        // THEN
        assertEquals(mockTransaction, result)
        verify(exactly = 1) { repository.getTransaction(txId) }
    }
}