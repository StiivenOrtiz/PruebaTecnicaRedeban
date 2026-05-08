package com.stiivenortiz.pruebatecnicaredeban.view.dashboard

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.stiivenortiz.pruebatecnicaredeban.MainActivity
import com.stiivenortiz.pruebatecnicaredeban.R
import com.stiivenortiz.pruebatecnicaredeban.data.di.DataModule
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionBusinessStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionInternalStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionModel
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionOperationType
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.AnnulmentTransactionRepository
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.AuthorizeTransactionRepository
import com.stiivenortiz.pruebatecnicaredeban.domain.repository.TransactionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Singleton

@HiltAndroidTest
@UninstallModules(DataModule::class)
@RunWith(AndroidJUnit4::class)
class DashboardScreenTest {

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    companion object {
        val fakeRepositoryFlow = MutableStateFlow<List<TransactionModel>>(emptyList())
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object TestDataModule {
        @Provides
        @Singleton
        fun provideTransactionRepository(): TransactionRepository = mockk {
            every { getTransactions() } returns fakeRepositoryFlow
        }

        @Provides
        @Singleton
        fun provideAuthorizeTransactionRepository(): AuthorizeTransactionRepository =
            mockk(relaxed = true)

        @Provides
        @Singleton
        fun provideAnnulmentTransactionRepository(): AnnulmentTransactionRepository =
            mockk(relaxed = true)
    }

    @Test
    fun dashboard_whenTransactions_Exist_showsFormattedTotal() {
        // GIVEN:
        val mockData = listOf(
            TransactionModel(
                id = 1L,
                amount = "25000",
                operationType = TransactionOperationType.SALE,
                businessStatus = TransactionBusinessStatus.APPROVED,
                internalStatus = TransactionInternalStatus.COMPLETED,
                timestamp = System.currentTimeMillis(),
                receiptId = "123456",
                businessStatusDescription = "ok",
                isVoided = false,
                maskedCardNumber = "1234 56** **** 7890"
            ),
            TransactionModel(
                id = 2L,
                amount = "25000",
                operationType = TransactionOperationType.SALE,
                businessStatus = TransactionBusinessStatus.APPROVED,
                internalStatus = TransactionInternalStatus.COMPLETED,
                timestamp = System.currentTimeMillis(),
                receiptId = "123456",
                businessStatusDescription = "ok",
                isVoided = false,
                maskedCardNumber = "1234 56** **** 7890"
            )
        )

        // WHEN
        fakeRepositoryFlow.value = mockData

        // THEN
        composeTestRule.onNodeWithText("$50.000").assertIsDisplayed()
    }

    @Test
    fun dashboard_whenEmpty_showsEmptyMessage() {
        // GIVEN
        fakeRepositoryFlow.value = emptyList()

        // THEN
        val emptyText = composeTestRule.activity.getString(R.string.dashboard_screen_empty_tx_text)
        composeTestRule.onNodeWithText(emptyText).assertIsDisplayed()
    }
}