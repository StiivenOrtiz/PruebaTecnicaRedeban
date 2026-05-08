package com.stiivenortiz.pruebatecnicaredeban.view.core.components.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.visible
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.stiivenortiz.pruebatecnicaredeban.R
import com.stiivenortiz.pruebatecnicaredeban.ui.theme.failed
import com.stiivenortiz.pruebatecnicaredeban.ui.theme.informative
import com.stiivenortiz.pruebatecnicaredeban.ui.theme.onState
import com.stiivenortiz.pruebatecnicaredeban.ui.theme.success
import com.stiivenortiz.pruebatecnicaredeban.view.core.mapper.toStringRes
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiModel
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiStatus

@Composable
fun TransactionCard(
    model: TransactionUiModel
) {
    val imageVector = when {
        model.isVoided -> Icons.Default.Info
        model.status == TransactionUiStatus.APPROVED -> Icons.Default.Check
        else -> Icons.Default.Close
    }

    val informativeColor = when {
        model.isVoided -> informative
        model.status == TransactionUiStatus.APPROVED -> success
        else -> failed
    }

    val visible = when (model.status) {
        TransactionUiStatus.APPROVED -> true
        TransactionUiStatus.DECLINED -> false
        TransactionUiStatus.PENDING -> false
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {

        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 18.dp),
            shape = RoundedCornerShape(20)
        ) {
            ConstraintLayout(
                Modifier
                    .fillMaxSize()
                    .padding(start = 22.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
            ) {
                val (amountElement, receiptElement, operationElement, cardElement, dateElement, statusElement, voidedElement) = createRefs()

                Text(
                    model.amount,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.headlineMedium,
                    textDecoration = if (model.isVoided) TextDecoration.LineThrough else TextDecoration.None,
                    modifier = Modifier.constrainAs(amountElement) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
                )

                Row(
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .constrainAs(cardElement) {
                            top.linkTo(amountElement.bottom)
                            start.linkTo(parent.start)
                            bottom.linkTo(receiptElement.top)
                        }
                        .visible(visible), verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_card),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 4.dp)
                            .size(20.dp),
                        tint = null
                    )

                    Text(
                        model.maskPan,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }

                TransactionColumnDescription(
                    modifier = Modifier.constrainAs(operationElement) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    },
                    title = stringResource(R.string.transaction_column_description_component_type_text),
                    value = stringResource(model.type.toStringRes())
                )

                TransactionColumnDescription(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .constrainAs(receiptElement) {
                            bottom.linkTo(parent.bottom)
                            start.linkTo(operationElement.end)
                            end.linkTo(statusElement.start)
                        }
                        .visible(visible),
                    title = stringResource(R.string.transaction_column_description_component_receipt_text),
                    value = model.receiptId
                )

                Text(
                    model.date,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.constrainAs(dateElement) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
                )

                Text(
                    stringResource(model.status.toStringRes()),
                    color = informativeColor,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.constrainAs(statusElement) {
                        top.linkTo(dateElement.bottom)
                        end.linkTo(parent.end)
                        bottom.linkTo(voidedElement.top)
                    }
                )

                Row(
                    modifier = Modifier
                        .constrainAs(voidedElement) {
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                        .offset(x = 12.dp)
                        .background(
                            informative, RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 0.dp,
                                bottomEnd = 0.dp,
                                bottomStart = 20.dp,
                            )
                        )
                        .padding(end = 20.dp, top = 4.dp, bottom = 4.dp, start = 4.dp)
                        .visible(model.isVoided)) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    Text(
                        stringResource(R.string.transaction_column_description_component_void_text),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .size(36.dp)
                .align(Alignment.CenterStart)
                .offset(x = (0).dp)
                .background(informativeColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                tint = onState
            )
        }
    }
}