package com.example.devnews.utils

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Failure(val message: String, val throwable: Throwable? = null) : ApiResult<Nothing>()
}