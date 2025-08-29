package com.example.devnews.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(apiCall: suspend () -> T): ApiResult<T> {
    return withContext(Dispatchers.IO) {
        try {
            ApiResult.Success(apiCall.invoke())
        } catch (e: Exception) {
            ApiResult.Failure(e.localizedMessage ?: "Unknown error", e)
        }
    }
}