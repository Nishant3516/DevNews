package com.example.devnews.utils

import android.content.Context
import android.content.Intent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun ShareButton( title: String, url: String?) {
    val context = LocalContext.current

    IconButton(onClick = {
        if (url != null) {
            shareNews(context, title, url)
        }
    }) {
        Icon(
            imageVector = Icons.Filled.Share, contentDescription = "Share"
        )
    }
}

private fun shareNews(context: Context, title: String, url: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, title)
        putExtra(Intent.EXTRA_TEXT, "$title\nRead more: $url")
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
}