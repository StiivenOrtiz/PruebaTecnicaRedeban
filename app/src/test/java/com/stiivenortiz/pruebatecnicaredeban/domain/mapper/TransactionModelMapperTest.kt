package com.stiivenortiz.pruebatecnicaredeban.domain.mapper

import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionBusinessStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionInternalStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionModel
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionOperationType
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.PaymentStatusProcess
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiStatus
import org.junit.Assert.assertEquals
import org.junit.Test

class TransactionModelMapperTest {

    @Test
    fun `toUiModel should format amount correctly for positive values`() {
        // GIVEN
        val model = createMockModel(amount = "50000")

        // WHEN
        val uiModel = model.toUiModel()

        // THEN
        assertEquals("$50.000", uiModel.amount)
    }

    @Test
    fun `toUiModel should format amount correctly for negative values`() {
        // GIVEN
        val model = createMockModel(amount = "-25000")

        // WHEN
        val uiModel = model.toUiModel()

        // THEN
        assertEquals("-$25.000", uiModel.amount)
    }

    @Test
    fun `toUiModel should map business status correctly`() {
        // GIVEN
        val approvedModel = createMockModel(businessStatus = TransactionBusinessStatus.APPROVED)
        val declinedModel = createMockModel(businessStatus = TransactionBusinessStatus.DECLINED)

        // WHEN
        val approvedUi = approvedModel.toUiModel()
        val declinedUi = declinedModel.toUiModel()

        // THEN
        assertEquals(TransactionUiStatus.APPROVED, approvedUi.status)
        assertEquals(TransactionUiStatus.DECLINED, declinedUi.status)
    }

    @Test
    fun `toUiPaymentProcess should map APPROVED status when COMPLETED and APPROVED`() {
        // GIVEN
        val model = createMockModel(
            internalStatus = TransactionInternalStatus.COMPLETED,
            businessStatus = TransactionBusinessStatus.APPROVED
        )

        // WHEN
        val process = model.toUiPaymentProcess()

        // THEN
        assertEquals(PaymentStatusProcess.APPROVED, process.paymentStatusProcess)
    }

    @Test
    fun `toUiPaymentProcess should map FAILED when business status is null`() {
        // GIVEN
        val model = createMockModel(
            internalStatus = TransactionInternalStatus.COMPLETED,
            businessStatus = null
        )

        // WHEN
        val process = model.toUiPaymentProcess()

        // THEN
        assertEquals(PaymentStatusProcess.FAILED, process.paymentStatusProcess)
    }

    private fun createMockModel(
        amount: String = "0",
        businessStatus: TransactionBusinessStatus? = TransactionBusinessStatus.APPROVED,
        internalStatus: TransactionInternalStatus = TransactionInternalStatus.COMPLETED
    ) = TransactionModel(
        id = 1L,
        amount = amount,
        maskedCardNumber = "1234 XXXX XXXX 5678",
        operationType = TransactionOperationType.SALE,
        receiptId = "REC123",
        timestamp = System.currentTimeMillis(),
        businessStatus = businessStatus,
        internalStatus = internalStatus,
        isVoided = false,
        businessStatusDescription = ""
    )
}