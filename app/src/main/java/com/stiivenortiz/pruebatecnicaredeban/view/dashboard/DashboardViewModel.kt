package com.stiivenortiz.pruebatecnicaredeban.view.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stiivenortiz.pruebatecnicaredeban.domain.mapper.toUiModel
import com.stiivenortiz.pruebatecnicaredeban.domain.model.TransactionBusinessStatus
import com.stiivenortiz.pruebatecnicaredeban.domain.usecase.GetTransactionsUseCase
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.TransactionUiStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getTransactionsUseCase: GetTransactionsUseCase
) : ViewModel() {

    val uiState: StateFlow<DashboardUiState> = getTransactionsUseCase()
        .map { transactions ->
            val totalRaw = transactions
                .filter { it.status == TransactionUiStatus.APPROVED }
                .sumOf {
                    it.amount
                        .replace("$", "")
                        .replace(".", "")
                        .replace(",", "")
                        .toLongOrNull() ?: 0L
                }

            DashboardUiState(
                isLoading = false,
                transactions = transactions,
                totalAmount = formatTotal(totalRaw)
            )
        }
        .onStart { emit(DashboardUiState(isLoading = true)) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DashboardUiState(isLoading = true)
        )

    private fun formatTotal(total: Long): String {
        return "$${"%,d".format(total).replace(",", ".")}"
    }
}