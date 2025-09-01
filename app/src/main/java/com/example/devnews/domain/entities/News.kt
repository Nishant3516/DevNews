package com.example.devnews.domain.entities

import java.util.Date

data class TaggedNews(
    val id: Int,
    val rawNews: RawNews,
    val newsType: NewsType?,
    val tags: List<Tag>,
    val categories: List<Category>,
    val createdAt: String,
    val updatedAt: String,
    val summary: String?,
    val likes: Int
)

data class RawNews(
    val id: Int,
    val source: Source,
    val sourceNewsId: String?,
    val title: String,
    val description: String?,
    val url: String,
    val sourceUrl: String?,
    val publishedAt: Date?,
    val fetchedAt: String,
    val createdAt: String,
    val updatedAt: String,
    val status: String,
    val imgUrl: String?,
    val slug: String,
)

data class Source(
    val id: Int,
    val name: String,
    val url: String,
    val iconUrl: String?,
)

data class NewsType(
    val id: Int,
    val type: String
)

