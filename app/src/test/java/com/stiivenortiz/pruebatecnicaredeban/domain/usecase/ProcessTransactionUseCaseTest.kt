package com.stiivenortiz.pruebatecnicaredeban.domain.usecase

import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionModel
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.AnnulmentTransactionRepository
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.AuthorizeTransactionRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class ProcessTransactionUseCaseTest {

    private val authRepository: AuthorizeTransactionRepository = mockk()
    private val annulRepository: AnnulmentTransactionRepository = mockk()

    private lateinit var processTransactionUseCase: ProcessTransactionUseCase

    @Before
    fun setup() {
        processTransactionUseCase = ProcessTransactionUseCase(authRepository, annulRepository)
    }

    @Test
    fun `when amount is provided, should call authTransaction and return success`(): Unit = runTest {
        // GIVEN
        val amount = "10000"
        val mockTransaction = mockk<TransactionModel>(relaxed = true)
        coEvery { authRepository.authTransaction(amount) } returns mockTransaction

        // WHEN
        val result = processTransactionUseCase(amount = amount)

        // THEN
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { authRepository.authTransaction(amount) }
        coVerify(exactly = 0) { annulRepository.annulTransaction(any()) }
    }


    @Test
    fun `when transactionId is provided, should call annulTransaction and return success`(): Unit = runTest {
        // GIVEN
        val txId = 123L
        val mockTransaction = mockk<TransactionModel>(relaxed = true)
        coEvery { annulRepository.annulTransaction(txId) } returns mockTransaction

        // WHEN
        val result = processTransactionUseCase(transactionId = txId)

        // THEN
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { annulRepository.annulTransaction(txId) }
        coVerify(exactly = 0) { authRepository.authTransaction(any()) }
    }

    @Test
    fun `when transactionId is provided but not found, should return failure`(): Unit = runTest {
        // GIVEN
        val txId = 999L
        coEvery { annulRepository.annulTransaction(txId) } returns null

        // WHEN
        val result = processTransactionUseCase(transactionId = txId)

        // THEN
        assertTrue(result.isFailure)
        assertEquals("Original transaction not found", result.exceptionOrNull()?.message)
    }

    @Test
    fun `when no parameters are provided, should return failure`(): Unit = runTest {
        // WHEN
        val result = processTransactionUseCase()

        // THEN
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is IllegalArgumentException)
    }
}