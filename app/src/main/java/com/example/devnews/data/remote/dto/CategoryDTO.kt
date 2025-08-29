package com.example.devnews.data.remote.dto

import com.example.devnews.domain.entities.Category
import com.example.devnews.domain.entities.Tag
import com.google.gson.annotations.SerializedName


data class CategoryDto(
    val id: Int,
    val name: String,
    val keywords: String?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
) {
    fun toDomain() = Category(
        id = id, name = name, keywords = keywords, createdAt = createdAt, updatedAt = updatedAt
    )
}

data class TagDto(
    val id: Int,
    val name: String,
    val keywords: String?,
    val category: CategoryDto,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
) {
    fun toDomain() = Tag(
        id = id,
        name = name,
        category = category.toDomain(),
        keywords = keywords,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
