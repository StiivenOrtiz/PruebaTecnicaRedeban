package com.stiivenortiz.pruebatecnicaredeban.view.transactiondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.stiivenortiz.pruebatecnicaredeban.R
import com.stiivenortiz.pruebatecnicaredeban.view.core.components.transactiondetail.DetailRow
import com.stiivenortiz.pruebatecnicaredeban.view.core.mapper.toStringRes
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.PaymentInput
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiModel
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiStatus
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiType

@Composable
fun TransactionDetailScreen(
    transactionId: Long,
    viewModel: TransactionDetailViewModel = hiltViewModel(),
    onVoid: (PaymentInput) -> Unit,
    onFinish: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val transaction = uiState.transaction

    Scaffold(
        bottomBar = {
            if (transaction != null) {
                BottomActions(transaction, onVoid, onFinish)
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator()

                transaction != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        TransactionHeaderCard(transaction)

                        Card(
                            shape = RoundedCornerShape(20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                DetailRow(
                                    stringResource(R.string.transaction_detail_id),
                                    transaction.id.toString()
                                )

                                if (transaction.maskPan.isNotBlank())
                                    DetailRow(
                                        stringResource(R.string.transaction_detail_card),
                                        transaction.maskPan
                                    )

                                DetailRow(
                                    stringResource(R.string.transaction_detail_type),
                                    stringResource(transaction.type.toStringRes())
                                )

                                if (transaction.receiptId.isNotBlank())
                                    DetailRow(
                                        stringResource(R.string.transaction_detail_receipt),
                                        transaction.receiptId
                                    )

                                DetailRow(
                                    stringResource(R.string.transaction_detail_date),
                                    transaction.date
                                )
                            }
                        }

                        if (transaction.isVoided)
                            VoidedBanner()
                    }
                }

                else -> Text(
                    text = uiState.errorMessage ?: "Transaction not found",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
private fun BottomActions(
    transaction: TransactionUiModel,
    onVoid: (PaymentInput) -> Unit,
    onFinish: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val canBeVoided = transaction.type == TransactionUiType.SALE &&
                transaction.status == TransactionUiStatus.APPROVED &&
                !transaction.isVoided

        if (canBeVoided) {
            Button(
                onClick = { onVoid(PaymentInput(transaction.id, transaction.amount)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text(
                    stringResource(R.string.transaction_detail_void_tx),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        Button(
            onClick = onFinish,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(
                text = stringResource(R.string.transaction_detail_close),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun VoidedBanner() {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.1f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.transaction_detail_void_alert),
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.error,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun TransactionHeaderCard(transaction: TransactionUiModel) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = transaction.amount,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(transaction.status.toStringRes()).uppercase(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}