package com.example.devnews.domain.usecases

import com.example.devnews.domain.entities.TaggedNews
import com.example.devnews.domain.repositories.NewsRepository
import com.example.devnews.utils.ApiResult

class GetNewsUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(categories: List<Int>? = null): ApiResult<List<TaggedNews>> {
        return repository.getNews(categories)
    }
}