package com.example.devnews.domain.entities


data class Category(
    val id: Int,
    val name: String,
    val keywords: String?,
    val createdAt: String,
    val updatedAt: String
)


data class Tag(
    val id: Int,
    val name: String,
    val keywords: String?,
    val createdAt: String,
    val updatedAt: String,
    val category: Category
)