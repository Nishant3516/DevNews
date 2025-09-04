package com.example.devnews.presentation.components.news

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.devnews.domain.entities.TaggedNews

@Composable
fun NewsPager(
    newsList: List<TaggedNews>,
    category: String,
    targetNewsSlug: String?,
    onLikeClick: (Int) -> Unit,
    onBookmarkClick: (TaggedNews) -> Unit,
    onShareClick: (String) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { newsList.size })

    VerticalPager(
        state = pagerState, modifier = Modifier.fillMaxSize()
    ) { page ->
        NewsItem(
            newsList[page],
            category,
            slugUrl = targetNewsSlug ?: newsList[page].rawNews.slug,
            onLikeClick = { onLikeClick(newsList[page].id) },
            onBookmarkClick = { onBookmarkClick(newsList[page]) },
            onShareClick = onShareClick
        )
    }
}