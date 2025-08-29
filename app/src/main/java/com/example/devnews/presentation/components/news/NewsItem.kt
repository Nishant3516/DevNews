package com.example.devnews.presentation.components.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.devnews.domain.entities.TaggedNews
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun NewsItem(news: TaggedNews, category: String) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val rawNews = news.rawNews

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = rawNews.imgUrl ?: rawNews.url,
            contentDescription = rawNews.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(LocalConfiguration.current.screenHeightDp.dp * 0.25f)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (category == "All") {
                news.categories.firstOrNull()?.takeIf { it.name != "Uncategorized" }
                    ?.let { NewsCategory(category = it.name) }
            } else {
                NewsCategory(category = category)
            }
            ActionRow()
        }
        Text(
            text = rawNews.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (!news.summary.isNullOrBlank()) {
            Text(
                text = news.summary,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .weight(1f, fill = true)
                    .fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(8.dp))
        } else {
            Spacer(
                modifier = Modifier
                    .weight(1f, fill = true)
                    .fillMaxWidth(),
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()
        ) {
            rawNews.publishedAt?.let {
                Text(
                    text = "Published: ${dateFormat.format(it)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = "Source #${rawNews.source.name}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
