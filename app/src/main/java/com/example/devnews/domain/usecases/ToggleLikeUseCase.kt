package com.example.devnews.domain.usecases

import com.example.devnews.data.remote.dto.LikeResponse
import com.example.devnews.domain.repositories.NewsRepository
import com.example.devnews.utils.ApiResult

class ToggleLikeUseCase(private val repository: NewsRepository) {
    suspend operator fun invoke(newsId: Int): ApiResult<LikeResponse> {
        return repository.toggleLike(newsId)
    }
}