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
class AuthorizeTransactionRepositoryTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: AuthorizeTransactionRepository

    @Inject
    lateinit var database: TransactionsDatabase

    @Before
    fun init() {
        // Esto inyecta las instancias REALES definidas en tus módulos de Hilt
        hiltRule.inject()
    }

    @Test
    fun testRealNetworkCallWithHilt() = runTest {
        val amount = "99999"
        val result = repository.authTransaction(amount)

        assertNotNull(result)
        assertEquals(amount, result.amount)

        val dbTx = database.getTransactionDao().getTransactionById(result.id)

        assertNotNull(dbTx)

        Log.d("TEST_RESULT", "MODELO (Domain): $result")
        Log.d("TEST_RESULT", "ENTIDAD (Room): $dbTx")

        assertEquals(TransactionInternalStatus.COMPLETED.name, dbTx?.internalStatus)

        Log.i("TEST_RESULT", "Prueba finalizada con éxito para el monto: ${result.amount}")
    }

}