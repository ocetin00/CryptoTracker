package com.oguzhan.shared.core

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String?, val exception: Exception? = null) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}