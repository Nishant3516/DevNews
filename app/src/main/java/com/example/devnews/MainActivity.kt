package com.example.devnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.devnews.ui.theme.DevNewsTheme
import com.example.devnews.utils.NavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DevNewsTheme(darkTheme = true) {
                val navController = rememberNavController()
                NavGraph(navHostController = navController)
            }
        }
    }
}