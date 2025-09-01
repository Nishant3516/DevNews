package com.example.devnews.domain.usecases

import com.example.devnews.domain.entities.TaggedNews
import com.example.devnews.domain.repositories.NewsRepository
import com.example.devnews.utils.ApiResult
import javax.inject.Inject

class GetNewsFromSlugUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke(slug: String): ApiResult<TaggedNews> {
        return repository.getNewsFromSlug(slug)
    }
}