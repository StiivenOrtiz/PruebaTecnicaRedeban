package com.stiivenortiz.pruebatecnicaredeban.view.paymentstatus

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.PaymentInput

enum class PaymentStatus {
    STARTING,
    PENDING,
    APPROVED,
    DECLINED,
    FAILED
}

@Composable
fun PaymentStatusScreen(
    input: PaymentInput,
    viewModel: PaymentStatusViewModel = hiltViewModel(),
    onFinished: (Long) -> Unit
) {

    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(input) {
        viewModel.startTransaction(input)
    }

    Scaffold(
        bottomBar = {

            if (
                state.status == PaymentStatus.APPROVED ||
                state.status == PaymentStatus.DECLINED ||
                state.status == PaymentStatus.FAILED
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .navigationBarsPadding()
                ) {

                    Button(
                        onClick = { onFinished(state.transactionId!!) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {

                        Text(
                            text = "Finalizar",
                            modifier = Modifier.padding(vertical = 6.dp),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {

            AnimatedContent(
                targetState = state.status,
                label = "payment_status_animation"
            ) { state ->

                when (state) {

                    PaymentStatus.STARTING -> {

                        PaymentStatusContent(
                            icon = {
                                RotatingIcon(
                                    imageVector = Icons.Default.Build
                                )
                            },
                            title = "Iniciando transacción",
                            description = "Estamos preparando el cobro"
                        )
                    }

                    PaymentStatus.PENDING -> {

                        PaymentStatusContent(
                            icon = {
                                RotatingIcon(
                                    imageVector = Icons.Default.Search
                                )
                            },
                            title = "Pago pendiente",
                            description = "Esperando confirmación del datáfono"
                        )
                    }

                    PaymentStatus.APPROVED -> {

                        PaymentStatusContent(
                            icon = {
                                StatusIcon(
                                    imageVector = Icons.Rounded.CheckCircle,
                                    backgroundColor = MaterialTheme.colorScheme.primary,
                                    iconColor = MaterialTheme.colorScheme.onPrimary
                                )
                            },
                            title = "Pago aprobado",
                            description = "La transacción fue realizada correctamente"
                        )
                    }

                    PaymentStatus.DECLINED -> {

                        PaymentStatusContent(
                            icon = {
                                StatusIcon(
                                    imageVector = Icons.Rounded.Close,
                                    backgroundColor = MaterialTheme.colorScheme.error,
                                    iconColor = MaterialTheme.colorScheme.onError
                                )
                            },
                            title = "Pago declinado",
                            description = "La entidad financiera rechazó la transacción"
                        )
                    }

                    PaymentStatus.FAILED -> {

                        PaymentStatusContent(
                            icon = {
                                StatusIcon(
                                    imageVector = Icons.Default.Close,
                                    backgroundColor = MaterialTheme.colorScheme.tertiary,
                                    iconColor = MaterialTheme.colorScheme.onTertiary
                                )
                            },
                            title = "Error en el pago",
                            description = "Ocurrió un problema procesando la transacción"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PaymentStatusContent(
    icon: @Composable () -> Unit,
    title: String,
    description: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        icon()

        Spacer(modifier = Modifier.size(28.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.size(12.dp))

        Text(
            text = description,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RotatingIcon(
    imageVector: androidx.compose.ui.graphics.vector.ImageVector
) {

    val infiniteTransition = rememberInfiniteTransition(
        label = "rotation_animation"
    )

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Box(
        modifier = Modifier
            .size(120.dp)
            .background(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .rotate(rotation),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun StatusIcon(
    imageVector: androidx.compose.ui.graphics.vector.ImageVector,
    backgroundColor: Color,
    iconColor: Color
) {

    Box(
        modifier = Modifier
            .size(120.dp)
            .background(
                color = backgroundColor.copy(alpha = 0.15f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = backgroundColor
        )
    }
}