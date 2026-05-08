package com.stiivenortiz.pruebatecnicaredeban.view.paymentamount

import androidx.lifecycle.ViewModel
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.PaymentInput
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PaymentAmountViewModel @Inject constructor() : ViewModel() {
    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("es", "CO")).apply {
        maximumFractionDigits = 0
    }

    fun processPayment(onSuccess: (PaymentInput) -> Unit) {
        if (_isLoading.value) return

        val currentAmount = _amount.value
        if (currentAmount.isNotEmpty() && (currentAmount.toDoubleOrNull() ?: 0.0) > 0) {
            _isLoading.value = true

            val paymentInput = PaymentInput(amount = currentAmount)
            onSuccess(paymentInput)
        }
    }

    fun onNumberClick(number: String) {
        if (_amount.value.length < 10) {
            _amount.update { it + number }
        }
    }

    fun onBackspaceClick() {
        if (_amount.value.isNotEmpty()) {
            _amount.update { it.dropLast(1) }
        }
    }

    fun getFormattedAmount(rawAmount: String): String {
        if (rawAmount.isEmpty()) return "$0"
        val number = rawAmount.toLongOrNull() ?: 0L
        return currencyFormatter.format(number).replace(",00", "")
    }
}