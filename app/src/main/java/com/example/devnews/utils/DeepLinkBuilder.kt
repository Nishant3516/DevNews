package com.example.devnews.utils

import com.example.devnews.di.annotations.BaseUrl
import javax.inject.Inject

class DeepLinkBuilder @Inject constructor(
    @BaseUrl private val baseUrl: String
) {
    fun newsLink(slug: String): String = "${baseUrl}news/$slug"
}
