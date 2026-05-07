package com.stiivenortiz.pruebatecnicaredeban.view.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun DashBoardScreen() {
    Scaffold() { paddingValues ->
        ConstraintLayout(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .fillMaxSize()
        ) { }
    }
}