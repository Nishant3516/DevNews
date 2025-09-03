package com.example.devnews.domain.usecases


import com.example.devnews.domain.entities.NewsMeta
import com.example.devnews.domain.repositories.NewsRepository
import com.example.devnews.utils.ApiResult
import javax.inject.Inject

class GetNewsMetaUseCase @Inject constructor(private val repository: NewsRepository) {
    suspend operator fun invoke(slugUrl: String): ApiResult<NewsMeta> {
        return repository.getNewsMeta(slugUrl)
    }
}