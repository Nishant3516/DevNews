package com.example.devnews.data.repository.impl

import com.example.devnews.data.remote.api.CategoryApi
import com.example.devnews.domain.entities.Category
import com.example.devnews.domain.repositories.CategoryRepository
import com.example.devnews.utils.ApiResult
import com.example.devnews.utils.safeApiCall

class CategoryRepoImpl(private val api: CategoryApi) : CategoryRepository {
    override suspend fun getCategory(): ApiResult<List<Category>> {
        return safeApiCall {
            api.getCategories().map { it.toDomain() }
        }
    }
}