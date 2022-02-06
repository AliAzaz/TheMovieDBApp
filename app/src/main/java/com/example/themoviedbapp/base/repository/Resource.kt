package com.example.themoviedbapp.base.repository

/**
 * @author AliAzazAlam on 5/16/2021.
 */
enum class ResponseStatus {
    SUCCESS,
    ERROR,
    LOADING
}

data class ResponseStatusCallbacks<out T>(
        val status: ResponseStatus,
        val data: T?,
        val message: String?
) {
    companion object {
        fun <T> success(data: T, message: String? = null): ResponseStatusCallbacks<T> =
                ResponseStatusCallbacks(status = ResponseStatus.SUCCESS, data = data, message = message)

        fun <T> error(data: T?, message: String): ResponseStatusCallbacks<T> =
                ResponseStatusCallbacks(status = ResponseStatus.ERROR, data = data, message = message)

        fun <T> loading(data: T?): ResponseStatusCallbacks<T> =
                ResponseStatusCallbacks(status = ResponseStatus.LOADING, data = data, message = null)
    }
}

sealed class ResultCallBack<out T> {
    data class Success<out T>(val data: T) : ResultCallBack<T>()
    data class Error<out T>(val error: String) : ResultCallBack<T>()
    data class CallException<out T>(val exception: Exception) : ResultCallBack<T>()
}
