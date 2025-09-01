package com.example.devnews.data.repository.impl

import com.example.devnews.data.remote.api.NewsApi
import com.example.devnews.data.remote.dto.LikeResponse
import com.example.devnews.domain.entities.TaggedNews
import com.example.devnews.domain.repositories.NewsRepository
import com.example.devnews.utils.ApiResult
import com.example.devnews.utils.safeApiCall

class NewsRepositoryImpl(private val api: NewsApi) : NewsRepository {
    override suspend fun getNews(categories: List<Int>?): ApiResult<List<TaggedNews>> {
        return safeApiCall {
            api.getNews(categories).map { it.toDomain() }
        }
    }

    override suspend fun toggleLike(newsId: Int): ApiResult<LikeResponse> {
        return safeApiCall {
            api.toggleNewsLike(newsId)
        }
    }

    override suspend fun getNewsFromSlug(slug: String): ApiResult<TaggedNews> {
        return safeApiCall {
            api.getNewsFromSlug(slug).toDomain()
        }
    }
}