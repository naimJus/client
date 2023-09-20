package com.example.data.model.exception

sealed class UserFetchException(message: String) : Exception(message) {
    object NetworkException : UserFetchException("Internet access not available, check network connection")
    object CacheNotAvailableException : UserFetchException("Cached data is not available. Try fetching remote data")
    data class UnknownException(val exception: Exception, override val message: String) : UserFetchException(message)
    data class NotFoundException(val userId: Int) : UserFetchException("No user found with id:$userId")
}