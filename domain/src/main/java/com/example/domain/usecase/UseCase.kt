package com.example.domain.usecase

interface UseCase<T, R> {

    suspend operator fun invoke(param: T): R
}