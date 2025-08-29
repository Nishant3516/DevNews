package com.example.devnews.data.remote.api

import com.example.devnews.data.remote.dto.CategoryDto
import retrofit2.http.GET

interface CategoryApi {
    @GET("category")
    suspend fun getCategories(): List<CategoryDto>
}