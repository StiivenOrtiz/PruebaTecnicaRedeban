package com.stiivenortiz.pruebatecnicaredeban.view.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.stiivenortiz.pruebatecnicaredeban.ui.theme.informative
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PaymentAmountScreen(
    onCancel: () -> Unit,
    onContinue: () -> Unit
) {

    var amount by remember {
        mutableStateOf("")
    }

    val formattedAmount = remember(amount) {

        if (amount.isEmpty()) {
            "$0"
        } else {

            val number = amount.toLongOrNull() ?: 0L

            NumberFormat
                .getCurrencyInstance(Locale("es", "CO"))
                .format(number)
                .replace(",00", "")
        }
    }

    Scaffold(
        bottomBar = {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
                    .navigationBarsPadding()
            ) {

                Button(
                    onClick = { onContinue() },
                    enabled = amount.isNotEmpty(),
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Pagar",
                        modifier = Modifier.padding(vertical = 6.dp),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Ingrese el monto",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.padding(10.dp))

                Text(
                    text = formattedAmount,
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.padding(16.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            NumericKeyboard(
                onNumberClick = { number ->
                    if (amount.length < 10) {
                        amount += number
                    }
                },
                onBackspaceClick = {
                    if (amount.isNotEmpty()) {
                        amount = amount.dropLast(1)
                    }
                },
                onCancelClick = { onCancel() }
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun NumericKeyboard(
    onNumberClick: (String) -> Unit,
    onBackspaceClick: () -> Unit,
    onCancelClick: () -> Unit
) {

    val keys = listOf(
        "1", "2", "3",
        "4", "5", "6",
        "7", "8", "9",
        "cancel", "0", "backspace"
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        userScrollEnabled = true
    ) {

        items(keys) { key ->
            when (key) {
                "cancel" -> {
                    KeyboardButton(
                        onClick = { onCancelClick() },
                        color = MaterialTheme.colorScheme.error
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onError,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                "backspace" -> {
                    KeyboardButton(
                        onClick = onBackspaceClick,
                        color = informative
                    ) {

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onError,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                else -> {
                    KeyboardButton(
                        onClick = {
                            onNumberClick(key)
                        }
                    ) {
                        Text(
                            text = key,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun KeyboardButton(
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    content: @Composable () -> Unit
) {

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clip(CircleShape)
            .clickable {
                onClick()
            },
        shape = CircleShape,
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}