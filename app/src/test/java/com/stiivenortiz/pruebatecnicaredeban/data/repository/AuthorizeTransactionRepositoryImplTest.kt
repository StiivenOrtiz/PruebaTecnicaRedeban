package com.stiivenortiz.pruebatecnicaredeban.data.repository

import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.dao.TransactionDao
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.api.TransactionApiService
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.dto.response.AuthorizationResponse
import com.stiivenortiz.pruebatecnicaredeban.data.security.SecurityProcessor
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionBusinessStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionInternalStatus
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AuthorizeTransactionRepositoryImplTest {

    private val api: TransactionApiService = mockk()
    private val dao: TransactionDao = mockk()
    private val securityProcessor: SecurityProcessor = mockk()
    private lateinit var repository: AuthorizeTransactionRepositoryImpl

    @Before
    fun setup() {
        repository = AuthorizeTransactionRepositoryImpl(api, dao, securityProcessor)
    }

    @Test
    fun `authTransaction should insert pending, call api, and update to completed when successful`() =
        runTest {
            // GIVEN
            val amount = "10000"
            val txId = 1L
            val apiResponse = mockk<Response<AuthorizationResponse>>()
            val responseBody = AuthorizationResponse(
                receiptId = "REC123",
                statusCode = "00",
                statusDescription = "Approved",
                hexData = "313233344435363738" // Hex for 1234D5678
            )

            coEvery { dao.insertTransaction(any()) } returns txId
            coEvery { api.authorizePayment(any()) } returns apiResponse
            every { apiResponse.isSuccessful } returns true
            every { apiResponse.body() } returns responseBody
            every { securityProcessor.generateHash(any()) } returns "hashed_pan"
            coEvery { dao.updateTransaction(any()) } just Runs

            // WHEN
            val result = repository.authTransaction(amount)

            // THEN
            assertEquals(TransactionInternalStatus.COMPLETED, result.internalStatus)
            assertEquals(TransactionBusinessStatus.APPROVED, result.businessStatus)
            coVerify(exactly = 1) { dao.insertTransaction(any()) }
            coVerify(exactly = 1) { api.authorizePayment(any()) }
            coVerify(exactly = 1) { dao.updateTransaction(any()) }
        }

    @Test
    fun `authTransaction should update to network error status when api call fails`() = runTest {
        // GIVEN
        val amount = "10000"
        val txId = 1L
        coEvery { dao.insertTransaction(any()) } returns txId
        coEvery { api.authorizePayment(any()) } throws Exception("Network timeout")
        coEvery { dao.updateTransaction(any()) } just Runs

        // WHEN
        val result = repository.authTransaction(amount)

        // THEN
        assertEquals(TransactionInternalStatus.NETWORK_ERROR, result.internalStatus)
        coVerify(exactly = 1) { dao.updateTransaction(any()) }
    }
}