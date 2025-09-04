package com.example.devnews.presentation.screens.news

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.devnews.domain.entities.TaggedNews
import com.example.devnews.presentation.components.news.HandleShareEffect
import com.example.devnews.presentation.components.news.NewsContent
import com.example.devnews.presentation.viewmodels.category.CategoryViewModel
import com.example.devnews.presentation.viewmodels.news.NewsViewModel
import com.example.devnews.utils.AppTopBar

@Composable
fun NewsScreen(
    targetNewsSlug: String? = null,
    newsViewModel: NewsViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    val state by newsViewModel.newsListState.collectAsState()
    val savedCategories by categoryViewModel.selectedCategories.collectAsState()
    val detailState by newsViewModel.newsDetailState.collectAsState()
    val context = LocalContext.current
    val shareState by newsViewModel.shareState.collectAsState()


    val categories = if (savedCategories.isEmpty()) {
        listOf("All", "AI", "Mobile", "Web", "Cloud", "Security")
    } else {
        listOf("All") + savedCategories.map { it.name }
    }

    var selectedCategory by remember { mutableStateOf("All") }
    val mergedNewsList = remember { mutableStateListOf<TaggedNews>() }

    LaunchedEffect(Unit) {
        categoryViewModel.loadSavedCategories()
    }

    LaunchedEffect(targetNewsSlug) {
        targetNewsSlug?.let { slug ->
            newsViewModel.getNewsFromSlug(slug)
        }
    }

    LaunchedEffect(selectedCategory) {
        if (selectedCategory == "All") {
            newsViewModel.fetchNews(savedCategories.map { it.id }.toList())
        } else {
            val selected = savedCategories.find { it.name == selectedCategory }
            selected?.let {
                newsViewModel.fetchNews(listOf(it.id))
            }
        }
    }

    HandleShareEffect(shareState, context)

    Scaffold(topBar = { AppTopBar() }) { padding ->
        NewsContent(
            state = state,
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it },
            detailState = detailState,
            mergedNewsList = mergedNewsList,
            targetNewsSlug = targetNewsSlug,
            onLikeClick = { newsViewModel.toggleLike(it) },
            onBookmarkClick = { /* TODO */ },
            onShareClick = { slug -> newsViewModel.shareNews(slug) },
            modifier = Modifier.padding(padding)
        )
    }
}

