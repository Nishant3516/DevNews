package com.example.devnews.domain.usecases

import com.example.devnews.domain.entities.Category
import com.example.devnews.domain.repositories.CategoryRepository
import com.example.devnews.utils.ApiResult
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(private val repository: CategoryRepository) {
    suspend operator fun invoke(): ApiResult<List<Category>> {
        return repository.getCategory()
    }
}