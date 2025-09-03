package com.example.devnews.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.devnews.BuildConfig
import com.example.devnews.presentation.screens.category.CategoriesScreen
import com.example.devnews.presentation.screens.news.NewsScreen

@Composable
fun NavGraph(navHostController: NavHostController) {
    val baseUrl = BuildConfig.BASE_URL

    NavHost(
        navController = navHostController,
        startDestination = "categories"
    ) {
        composable("categories") {
            CategoriesScreen(navController = navHostController)
        }
        composable("news") {
            NewsScreen(targetNewsSlug = null)
        }

        composable(
            "news/{newsSlug}",
            arguments = listOf(navArgument("newsSlug") { type = NavType.StringType }),
            deepLinks = listOf(navDeepLink { uriPattern = "https://${baseUrl}/news/{newsSlug}" })
        ) { backStackEntry ->
            val newsSlug = backStackEntry.arguments?.getString("newsSlug")
            NewsScreen(targetNewsSlug = newsSlug)
        }
    }
}