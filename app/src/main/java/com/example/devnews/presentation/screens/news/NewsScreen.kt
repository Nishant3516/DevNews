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
    newsViewModel: NewsViewModel = hiltViewModel(),
    categoryViewModel: CategoryViewModel = hiltViewModel()
) {
    val state by newsViewModel.uiState.collectAsState()
    val savedCategories by categoryViewModel.selectedCategories.collectAsState()

    val categories = if (savedCategories.isEmpty()) {
        listOf("All", "AI", "Mobile", "Web", "Cloud", "Security")
    } else {
        listOf("All") + savedCategories.map { it.name }
    }
    var selectedCategory by remember { mutableStateOf("All") }

    LaunchedEffect(Unit) {
        categoryViewModel.loadSavedCategories()
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
                    val newsList = (state as UiState.Success<List<TaggedNews>>).data
                    val pagerState = rememberPagerState(pageCount = { newsList.size })

                    VerticalPager(
                        state = pagerState, modifier = Modifier.fillMaxSize()
                    ) { page ->
                        NewsItem(newsList[page], selectedCategory)
                    }
                }
            }
        }
    }
}
