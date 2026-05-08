package com.stiivenortiz.pruebatecnicaredeban.domain.usecase

import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionModel
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.TransactionRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetTransactionsUseCaseTest {

    private val repository: TransactionRepository = mockk()
    private lateinit var getTransactionsUseCase: GetTransactionsUseCase

    @Before
    fun setup() {
        getTransactionsUseCase = GetTransactionsUseCase(repository)
    }

    @Test
    fun `should return flow of ui models when repository emits transactions`() = runTest {
        // GIVEN
        val mockData = listOf(
            mockk<TransactionModel>(relaxed = true),
            mockk<TransactionModel>(relaxed = true)
        )
        every { repository.getTransactions() } returns flowOf(mockData)

        // WHEN
        val result = getTransactionsUseCase().first()

        // THEN
        assertEquals(mockData.size, result.size)
        verify(exactly = 1) { repository.getTransactions() }
    }
}