package com.example.devnews.presentation.components.news

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.devnews.domain.entities.NewsMeta
import com.example.devnews.utils.UiState

@Composable
fun HandleShareEffect(shareState: UiState<NewsMeta>, context: Context) {
    LaunchedEffect(shareState) {
        when (shareState) {
            is UiState.Success -> {
                val meta = shareState.data
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, meta.title)
                    putExtra(Intent.EXTRA_TEXT, "${meta.title}\n\n${meta.url}")
                }
                context.startActivity(Intent.createChooser(shareIntent, "Share via"))
            }

            is UiState.Failure -> {
                Toast.makeText(
                    context,
                    "Unable to share news: ${shareState.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> Unit
        }
    }
}
