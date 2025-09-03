package com.example.devnews.data.remote.api

import com.example.devnews.data.remote.dto.LikeResponse
import com.example.devnews.data.remote.dto.TaggedNewsDto
import com.example.devnews.domain.entities.NewsMeta
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsApi {
    @GET("news")
    suspend fun getNews(
        @Query("category") categories: List<Int>? = null
    ): List<TaggedNewsDto>

    @POST("news/like/{id}/")
    suspend fun toggleNewsLike(@Path("id") newsId: Int): LikeResponse

    @GET("news/api/meta/{slug}/")
    suspend fun getNewsMeta(@Path("slug") slug: String): NewsMeta

    @GET("news/{slug}/")
    suspend fun getNewsFromSlug(@Path("slug") slug: String): TaggedNewsDto

}

