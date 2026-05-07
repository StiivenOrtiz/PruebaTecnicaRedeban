package com.stiivenortiz.pruebatecnicaredeban.view.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        DashboardUiState(
            isLoading = true
        )
    )

    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadTransactions()
    }

    private fun loadTransactions() {

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }

            delay(5000)

            val mockTransactions = List(10) { index ->

                val amount = ((1..9).random() * 100_000).toString()

                TransactionUiModel(
                    id = index.toLong(),

                    amount = "$$amount",

                    maskPan = buildString {
                        append((1000..9999).random())
                        append(" ")
                        append((10..99).random())
                        append("** **** ")
                        append((1000..9999).random())
                    },

                    type = listOf(
                        "Compra",
                        "Anulación",
                        "Pago"
                    ).random(),

                    receiptId = (10000000..99999999)
                        .random()
                        .toString(),

                    date = listOf(
                        "8:27 am | 7 mayo, 2026",
                        "2:10 pm | 6 mayo, 2026",
                        "11:42 am | 5 mayo, 2026",
                        "9:05 am | 4 mayo, 2026"
                    ).random(),

                    status = listOf(
                        "Aprobada",
                        "Pendiente",
                        "Rechazada"
                    ).random(),

                    isVoided = listOf(
                        true,
                        false
                    ).random()
                )
            }

            val totalAmount = mockTransactions.sumOf {

                it.amount
                    .replace("$", "")
                    .toLongOrNull() ?: 0
            }

            _uiState.update {

                it.copy(
                    isLoading = false,

                    transactions = mockTransactions,

                    totalAmount = "$${"%,d"
                        .format(totalAmount)
                        .replace(",", ".")}"
                )
            }
        }
    }
}