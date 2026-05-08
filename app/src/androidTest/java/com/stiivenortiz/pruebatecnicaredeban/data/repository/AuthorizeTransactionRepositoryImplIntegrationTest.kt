package com.stiivenortiz.pruebatecnicaredeban.data.repository

import android.util.Log
import com.stiivenortiz.pruebatecnicaredeban.data.datasource.database.TransactionsDatabase
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionInternalStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.AuthorizeTransactionRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AuthorizeTransactionRepositoryImplIntegrationTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: AuthorizeTransactionRepository

    @Inject
    lateinit var database: TransactionsDatabase

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun givenValidAmount_whenAuthTransactionExecuted_thenTransactionIsPersistedAndReturned() = runTest {
        // Given
        val amount = "99999"

        // When
        val result = repository.authTransaction(amount)

        // Then - result validation
        assertNotNull(result)
        assertEquals(amount, result.amount)

        // Then - persistence validation
        val dbTx = database.getTransactionDao().getTransactionById(result.id)

        assertNotNull(dbTx)
        assertEquals(TransactionInternalStatus.COMPLETED.name, dbTx?.internalStatus)

        // Logs (debug only, no assertions)
        Log.d("TEST_RESULT", "MODEL (Domain): $result")
        Log.d("TEST_RESULT", "ENTITY (Room): $dbTx")
    }

}