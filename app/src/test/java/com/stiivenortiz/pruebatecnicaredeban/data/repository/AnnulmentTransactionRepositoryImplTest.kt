package com.stiivenortiz.pruebatecnicaredeban.data.repository

import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.dao.TransactionDao
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.entities.TransactionEntity
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.api.TransactionApiService
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.dto.response.AnnulmentResponse
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionOperationType
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AnnulmentTransactionRepositoryImplTest {

    private val api: TransactionApiService = mockk()
    private val dao: TransactionDao = mockk()
    private lateinit var repository: AnnulmentTransactionRepositoryImpl

    @Before
    fun setup() {
        repository = AnnulmentTransactionRepositoryImpl(api, dao)
    }

    @Test
    fun `annulTransaction should return null when parent transaction is not found`() = runTest {
        // GIVEN
        val id = 999L
        coEvery { dao.getTransactionById(id) } returns null

        // WHEN
        val result = repository.annulTransaction(id)

        // THEN
        assertNull(result)
        coVerify(exactly = 0) { api.annulPayment(any()) }
    }

    @Test
    fun `annulTransaction should call api and update both transactions when successful`() =
        runTest {
            // GIVEN
            val id = 1L
            val parentEntity = mockk<TransactionEntity>(relaxed = true) {
                every { amount } returns "5000"
                every { receiptId } returns "REC123"
            }
            val apiResponse = mockk<Response<AnnulmentResponse>>()
            val responseBody = AnnulmentResponse(statusCode = "00", statusDescription = "Voided")

            coEvery { dao.getTransactionById(id) } returns parentEntity
            coEvery { dao.insertTransaction(any()) } returns 2L
            coEvery { api.annulPayment(any()) } returns apiResponse
            every { apiResponse.isSuccessful } returns true
            every { apiResponse.body() } returns responseBody
            coEvery { dao.updateTransaction(any()) } just Runs

            // WHEN
            val result = repository.annulTransaction(id)

            // THEN
            assertNotNull(result)
            assertEquals(TransactionOperationType.VOID, result?.operationType)
            coVerify(exactly = 2) { dao.updateTransaction(any()) }
            coVerify(exactly = 1) { api.annulPayment(any()) }
        }
}