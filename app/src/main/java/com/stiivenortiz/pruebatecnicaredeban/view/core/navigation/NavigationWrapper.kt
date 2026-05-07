package com.stiivenortiz.pruebatecnicaredeban.view.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.stiivenortiz.pruebatecnicaredeban.view.dashboard.DashBoardScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Dashboard) {
        composable<Dashboard> {
            DashBoardScreen()
        }
    }
}