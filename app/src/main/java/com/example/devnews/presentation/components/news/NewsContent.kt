package com.example.devnews.presentation.components.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.devnews.domain.entities.TaggedNews
import com.example.devnews.utils.UiState

@Composable
fun NewsContent(
    state: UiState<List<TaggedNews>>,
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    detailState: UiState<TaggedNews>,
    mergedNewsList: MutableList<TaggedNews>,
    targetNewsSlug: String?,
    onLikeClick: (Int) -> Unit,
    onBookmarkClick: (TaggedNews) -> Unit,
    onShareClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        CategoriesRow(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = onCategorySelected,
        )
        when (state) {
            is UiState.Loading -> Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }

            is UiState.Failure -> {
                Text(state.message)
            }

            is UiState.Success -> {
                val feed = state.data

                // Refresh merged list whenever feed updates
                mergedNewsList.clear()
                mergedNewsList.addAll(feed)

                // Prepend target news if needed
                LaunchedEffect(detailState) {
                    if (detailState is UiState.Success) {
                        val fetched = detailState.data
                        if (mergedNewsList.none { n -> n.rawNews.slug == fetched.rawNews.slug }) {
                            mergedNewsList.add(0, fetched)
                        }
                    }
                }

                NewsPager(
                    newsList = mergedNewsList,
                    category = selectedCategory,
                    targetNewsSlug = targetNewsSlug,
                    onLikeClick = onLikeClick,
                    onBookmarkClick = onBookmarkClick,
                    onShareClick = onShareClick
                )
            }
        }
    }
}