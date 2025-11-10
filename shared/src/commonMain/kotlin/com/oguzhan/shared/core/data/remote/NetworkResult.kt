package com.oguzhan.shared.core.data.remote

import kotlinx.coroutines.coroutineScope
/*

sealed class NetworkResult<T> {
    data class Success<T>(val data: T?) : NetworkResult<T>()
    data class Error<T>(val message: String, val errors: List<String?>? = null) :
        NetworkResult<T>()
}


inline fun <T> safeApiCall(apiCall: () -> BaseModel<T>): NetworkResult<T> {
    return try {
        val result = apiCall.invoke()
        if (result.isSuccess == false) {
            NetworkResult.Error(
                message = result.message ?: "",
                errors = result.errors
            )
        } else {
            NetworkResult.Success(data = result.data)

        }
    } catch (e: Exception) {
        NetworkResult.Error(message = e.message ?: "")
    }
}
*/
