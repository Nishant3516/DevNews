package com.example.devnews.presentation.screens.category

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.devnews.presentation.viewmodels.category.CategoryViewModel
import com.example.devnews.utils.UiState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CategoriesScreen(
    navController: NavController,
    viewModel: CategoryViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    val selectedCategories by viewModel.selectedCategories.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
        viewModel.loadSavedCategories()
    }

    Scaffold(topBar = { TopAppBar(title = { Text("Dev News") }) }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            when (state) {
                is UiState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }

                is UiState.Failure -> {
                    Log.d("error", (state as UiState.Failure).message)
                    Text((state as UiState.Failure).message)
                }

                is UiState.Success -> {
                    val categories = (state as UiState.Success).data

                    Text("Select Categories you want to enroll")
                    FlowRow {
                        categories.forEach { category ->
                            val isSelected = selectedCategories.any { it.id == category.id }
                            Text(
                                text = category.name,
                                modifier = Modifier
                                    .padding(end = 8.dp, top = 4.dp, bottom = 4.dp)
                                    .background(
                                        if (isSelected) Color.Green else Color.Gray,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(8.dp)
                                    .clickable {
                                        viewModel.toggleCategorySelection(category)
                                    },
                                color = if (isSelected) Color.Black else Color.White,
                            )
                        }
                    }
                    Button(onClick = {
                        viewModel.saveSelectedCategories()
                        navController.navigate("news")
                    }) {
                        Text("Start")
                    }
                }
            }
        }
    }
}