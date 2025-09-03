package com.example.devnews.domain.entities

data class NewsMeta(
    val title: String,
    val description: String?,
    val image: String?,
    val url: String,
    val likes: Int
)
