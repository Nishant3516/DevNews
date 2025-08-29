package com.example.devnews.presentation.components.news

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ActionRow() {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .border(width = 1.dp, color = Color.Gray, shape = RoundedCornerShape(12.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = { /* Like action */ }, modifier = Modifier.padding(0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite, contentDescription = "Like"
                )
            }
            IconButton(onClick = { /* Share action */ }) {
                Icon(
                    imageVector = Icons.Filled.Share, contentDescription = "Share"
                )
            }
            IconButton(onClick = { /* Bookmark action */ }) {
                Icon(
                    imageVector = Icons.Default.Warning, contentDescription = "Bookmark"
                )
            }
        }
    }
}
