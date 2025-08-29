package com.example.devnews.data.remote.dto

import com.example.devnews.domain.entities.Category
import com.example.devnews.domain.entities.NewsType
import com.example.devnews.domain.entities.RawNews
import com.example.devnews.domain.entities.Source
import com.example.devnews.domain.entities.Tag
import com.example.devnews.domain.entities.TaggedNews
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class TaggedNewsDto(
    val id: Int,
    @SerializedName("raw_news") val rawNews: RawNewsDto,
    @SerializedName("news_type") val newsType: NewsTypeDto?,
    val tags: List<TagDto>,
    val categories: List<CategoryDto>,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    val summary: String? = null
) {
    fun toDomain(): TaggedNews {
        return TaggedNews(
            id = id,
            rawNews = rawNews.toDomain(),
            newsType = newsType?.toDomain(),
            tags = tags.map { it.toDomain() },
            categories = categories.map { it.toDomain() },
            createdAt = createdAt.toDateOrNow().toString(),
            updatedAt = updatedAt.toDateOrNow().toString(),
            summary = summary

        )
    }
}

data class RawNewsDto(
    val id: Int,
    val title: String,
    val description: String?,
    val url: String,
    @SerializedName("source_url") val sourceUrl: String?,
    val source: SourceDto,
    @SerializedName("source_news_id") val sourceNewsId: String?,
    @SerializedName("published_at") val publishedAt: String?,
    @SerializedName("fetched_at") val fetchedAt: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    val status: String,
    @SerializedName("img_url") val imgUrl: String?
) {
    fun toDomain(): RawNews {
        return RawNews(
            id = id,
            title = title,
            description = description,
            url = url,
            sourceUrl = sourceUrl,
            source = source.toDomain(),
            sourceNewsId = sourceNewsId,
            publishedAt = publishedAt.toDateOrNull(),
            fetchedAt = fetchedAt,
            createdAt = createdAt,
            updatedAt = updatedAt,
            status = status,
            imgUrl = imgUrl
        )
    }
}

data class SourceDto(
    val id: Int, val name: String, val url: String, @SerializedName("icon_url") val iconUrl: String?
) {
    fun toDomain() = Source(
        id = id,
        name = name,
        url = url,
        iconUrl = iconUrl,
    )
}

data class NewsTypeDto(
    val id: Int, val type: String
) {
    fun toDomain() = NewsType(
        id = id, type = type
    )
}

private fun String.toDateOrNow(): Date {
    val formats = listOf(
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss'Z'"
    )
    return formats.asSequence().map { fmt ->
            runCatching {
                SimpleDateFormat(fmt, Locale.US).parse(this)
            }.getOrNull()
        }.firstOrNull { it != null } ?: Date()
}

private fun String?.toDateOrNull(): Date? {
    if (this == null) return null
    val formats = listOf(
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss'Z'"
    )
    return formats.asSequence().map { fmt ->
            runCatching {
                SimpleDateFormat(fmt, Locale.US).parse(this)
            }.getOrNull()
        }.firstOrNull { it != null }
}
