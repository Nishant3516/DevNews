package com.example.devnews.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.devnews.presentation.screens.category.CategoriesScreen
import com.example.devnews.presentation.screens.news.NewsScreen

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(
        navController = navHostController,
        startDestination = "categories"
    ){
        composable("categories") {
            CategoriesScreen(navController = navHostController)
        }
        composable("news") {
            NewsScreen()
        }
    }
}