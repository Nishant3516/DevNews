package com.example.devnews.presentation.components.news

import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.devnews.domain.entities.TaggedNews
import java.text.SimpleDateFormat
import java.util.Locale


@Composable
fun NewsItem(
    news: TaggedNews, category: String, onLikeClick: (Int) -> Unit,
    onBookmarkClick: (TaggedNews) -> Unit,
    slugUrl: String?,
    onShareClick: (String) -> Unit,
) {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    val rawNews = news.rawNews

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
            .border(shape = RoundedCornerShape(16.dp), width = 0.dp, color = Color.Black)
    ) {
        NewsItemImage(
            imgUrl = rawNews.imgUrl,
            url = rawNews.url,
            id = rawNews.id,
            title = rawNews.title
        )
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
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
                rawNews.sourceUrl?.let {
                    ActionRow(
                        likes = news.likes,
                        text = rawNews.title,
                        url = slugUrl,
                        onLikeClick = { onLikeClick(news.id) },
                        onBookmarkClick = { onBookmarkClick(news) },
                        onShareClick = { slugUrl?.let { onShareClick(it) } }
                    )
                }
            }
            Text(
                text = rawNews.title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            NewsSummary(
                modifier = Modifier.weight(1f),
                summary = news.summary
            )
            Spacer(modifier = Modifier.height(8.dp))
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
}

