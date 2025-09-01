package com.example.devnews.presentation.screens.news

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.devnews.domain.entities.TaggedNews
import com.example.devnews.presentation.components.news.CategoriesRow
import com.example.devnews.presentation.components.news.NewsItem
import com.example.devnews.presentation.viewmodels.category.CategoryViewModel
import com.example.devnews.presentation.viewmodels.news.NewsViewModel
import com.example.devnews.utils.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    targetNewsSlug: String? = null,
    newsViewModel: NewsViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    val state by newsViewModel.newsListState.collectAsState()
    val savedCategories by categoryViewModel.selectedCategories.collectAsState()

    val categories = if (savedCategories.isEmpty()) {
        listOf("All", "AI", "Mobile", "Web", "Cloud", "Security")
    } else {
        listOf("All") + savedCategories.map { it.name }
    }
    var selectedCategory by remember { mutableStateOf("All") }
    val mergedNewsList = remember { mutableStateListOf<TaggedNews>() }
    val detailState by newsViewModel.newsDetailState.collectAsState()

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

    Scaffold(topBar = { TopAppBar(title = { Text("Dev News") }) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            CategoriesRow(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
            )
            when (state) {
                is UiState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }

                is UiState.Failure -> {
                    Log.d("error", (state as UiState.Failure).message)
                    Text((state as UiState.Failure).message)
                }

                is UiState.Success -> {
                    val feed = (state as UiState.Success<List<TaggedNews>>).data

                    // Refresh merged list whenever feed updates
                    mergedNewsList.clear()
                    mergedNewsList.addAll(feed)

                    // Prepend target news if needed
                    LaunchedEffect(detailState) {
                        if (detailState is UiState.Success) {
                            val fetched = (detailState as UiState.Success).data
                            if (mergedNewsList.none { n -> n.rawNews.slug == fetched.rawNews.slug }) {
                                mergedNewsList.add(0, fetched)
                            }
                        }
                    }
                    val pagerState = rememberPagerState(pageCount = { mergedNewsList.size })


                    VerticalPager(
                        state = pagerState, modifier = Modifier.fillMaxSize()
                    ) { page ->
                        NewsItem(
                            mergedNewsList[page],
                            selectedCategory,
                            slugUrl = newsViewModel.buildDeepLink(
                                targetNewsSlug ?: mergedNewsList[page].rawNews.slug
                            ),
                            onLikeClick = { newsViewModel.toggleLike(mergedNewsList[page].id) },
//                            onBookmarkClick = { n -> viewModel.saveBookmark(n) },
                            onBookmarkClick = { },
                        )
                    }
                }
            }
        }
    }
}
