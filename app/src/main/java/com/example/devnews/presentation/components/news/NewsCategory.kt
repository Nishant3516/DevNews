package com.example.devnews.presentation.components.news

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NewsCategory(category: String) {
    Box(
        modifier = Modifier
            .border(1.dp, color = Color.Gray)
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Text(text = category)
    }
}