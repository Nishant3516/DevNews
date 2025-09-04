package com.example.devnews.presentation.components.news


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NewsSummary(modifier: Modifier = Modifier, summary: String?) {
    if (!summary.isNullOrBlank()) {
        Text(
            text = summary,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = modifier
                .fillMaxWidth(),
        )
    } else {
        Spacer(
            modifier = modifier
                .fillMaxWidth(),
        )
    }
}