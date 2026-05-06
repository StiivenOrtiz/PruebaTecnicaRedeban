package com.stiivenortiz.pruebatecnicaredeban

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.stiivenortiz.pruebatecnicaredeban.ui.theme.PruebaTecnicaRedebanTheme
import com.stiivenortiz.pruebatecnicaredeban.view.core.navigation.NavigationWrapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PruebaTecnicaRedebanTheme {
                NavigationWrapper()
            }
        }
    }
}