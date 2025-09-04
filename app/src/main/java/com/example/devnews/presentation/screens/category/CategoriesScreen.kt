package com.example.devnews.presentation.screens.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.devnews.presentation.components.category.CategoryOption
import com.example.devnews.presentation.components.category.EnrollText
import com.example.devnews.presentation.components.category.StartButton
import com.example.devnews.presentation.viewmodels.category.CategoryViewModel
import com.example.devnews.utils.AppTopBar
import com.example.devnews.utils.UiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoriesScreen(
    navController: NavController, viewModel: CategoryViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val selectedCategories by viewModel.selectedCategories.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
        viewModel.loadSavedCategories()
    }

    Scaffold(topBar = {
        AppTopBar()
    }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            when (state) {
                is UiState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { CircularProgressIndicator(color = MaterialTheme.colorScheme.primary) }

                is UiState.Failure -> {
                    Text(
                        (state as UiState.Failure).message, color = MaterialTheme.colorScheme.error
                    )
                }

                is UiState.Success -> {
                    val categories = (state as UiState.Success).data
                    EnrollText()
                    FlowRow {
                        categories.forEach { category ->
                            val isSelected = selectedCategories.any { it.id == category.id }
                            CategoryOption(
                                category = category,
                                isSelected = isSelected,
                                onClick = { viewModel.toggleCategorySelection(category) },
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    StartButton(onClick = {
                        viewModel.saveSelectedCategories()
                        navController.navigate("news")
                    })
                }
            }
        }
    }
}