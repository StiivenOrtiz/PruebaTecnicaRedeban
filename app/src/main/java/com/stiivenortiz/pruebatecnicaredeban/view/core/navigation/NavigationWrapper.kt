package com.stiivenortiz.pruebatecnicaredeban.view.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.stiivenortiz.pruebatecnicaredeban.view.core.model.PaymentInput
import com.stiivenortiz.pruebatecnicaredeban.view.core.navigation.types.paymentInputType
import com.stiivenortiz.pruebatecnicaredeban.view.dashboard.DashboardScreen
import com.stiivenortiz.pruebatecnicaredeban.view.paymentamount.PaymentAmountScreen
import com.stiivenortiz.pruebatecnicaredeban.view.paymentstatus.PaymentStatusScreen
import com.stiivenortiz.pruebatecnicaredeban.view.transactiondetail.TransactionDetailScreen
import kotlin.reflect.typeOf

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Dashboard
    ) {

        composable<Dashboard> {
            DashboardScreen(
                onTransactionClick = { id ->
                    navController.navigateSafe(
                        TransactionDetail(transactionId = id)
                    )
                },
                onPayClick = {
                    navController.navigateSafe(PaymentAmount)
                }
            )
        }

        composable<TransactionDetail> { backStackEntry ->
            val args = backStackEntry.toRoute<TransactionDetail>()

            TransactionDetailScreen(
                transactionId = args.transactionId,
                onVoid = {
                    navController.navigateSafe(PaymentStatus(paymentInput = it))
                },
                onFinish = {
                    navController.popBackStackSafe<TransactionDetail>()
                }
            )
        }

        composable<PaymentAmount> {
            PaymentAmountScreen(
                onCancel = {
                    navController.popBackStackSafe<PaymentAmount>()
                },
                onContinue = {
                    navController.navigateSafe(PaymentStatus(paymentInput = it)) {
                        popUpTo(Dashboard) { inclusive = false }
                    }
                }
            )
        }

        composable<PaymentStatus>(typeMap = mapOf(typeOf<PaymentInput>() to paymentInputType)) { backStackEntry ->
            val args = backStackEntry.toRoute<PaymentStatus>()

            PaymentStatusScreen(
                input = args.paymentInput,
                onFinished = { transactionId ->
                    navController.navigateSafe(
                        TransactionDetail(transactionId)
                    ) {
                        popUpTo(Dashboard) { inclusive = false }
                    }
                }
            )
        }
    }
}