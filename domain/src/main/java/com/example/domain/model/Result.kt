package com.example.domain.model

sealed class Result<out A, out B : Exception> {
    data class Success<A>(val data: A) : Result<A, Nothing>()
    data class Error<B : Exception>(val error: B) : Result<Nothing, B>()
}