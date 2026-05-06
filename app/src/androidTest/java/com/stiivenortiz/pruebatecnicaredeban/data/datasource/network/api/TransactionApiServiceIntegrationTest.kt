package com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.api

import com.stiivenortiz.pruebatecnicaredeban.data.datasource.network.dto.request.AuthorizationRequest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject


@HiltAndroidTest
class TransactionApiServiceIntegrationTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var api: TransactionApiService

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun shouldSucceedWhenCallingAuthorizePaymentApiSmokeTest() = runTest {
        // Given: a valid authorization request
        val request = AuthorizationRequest(amount = "15000")

        // When: the authorize payment endpoint is called
        val response = api.authorizePayment(request)

        // Then: the response should be successful and contain a body
        assertTrue("The request must be successful", response.isSuccessful)
        assertNotNull("The body of the response must not be null", response.body())
    }
}