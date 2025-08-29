package com.example.devnews.data.remote.api

import com.example.devnews.data.remote.dto.TaggedNewsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("news")
    suspend fun getNews(
        @Query("category") categories: List<Int>? = null
    ): List<TaggedNewsDto>
}