package com.example.root_detector.common

sealed class Resource<T>(
    val data: T? = null,
    errorCode: Int? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class ServerError<T>(message: String) : Resource<T>(message = message)
    class Error<T>(errorCode: Int) : Resource<T>(errorCode = errorCode)
    class AruconDontFound<T>(message: String) : Resource<T>(message = message)
}