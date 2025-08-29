package com.example.devnews.domain.repositories

import com.example.devnews.domain.entities.Category
import com.example.devnews.utils.ApiResult

interface CategoryRepository {
    suspend fun getCategory(): ApiResult<List<Category>>
}