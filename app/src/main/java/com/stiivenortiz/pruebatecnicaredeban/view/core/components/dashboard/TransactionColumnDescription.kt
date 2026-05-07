package com.stiivenortiz.pruebatecnicaredeban.view.core.components.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TransactionColumnDescription(modifier: Modifier = Modifier, title: String, value: String) {
    Column(
        modifier = modifier
    ) {
        Text(
            title,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.titleSmall
        )
        Text(
            value,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelMedium
        )
    }
}