package com.example.devnews.domain.repositories

import com.example.devnews.data.remote.dto.LikeResponse
import com.example.devnews.domain.entities.NewsMeta
import com.example.devnews.domain.entities.TaggedNews
import com.example.devnews.utils.ApiResult

interface NewsRepository {
    suspend fun getNews(categories: List<Int>? = null): ApiResult<List<TaggedNews>>
    suspend fun toggleLike(newsId: Int): ApiResult<LikeResponse>
    suspend fun getNewsFromSlug(slug: String): ApiResult<TaggedNews>
    suspend fun getNewsMeta(slugUrl: String): ApiResult<NewsMeta>
}