package com.example.domain.usecase

import com.example.data.model.User
import com.example.data.model.exception.UserFetchException
import com.example.data.repository.UserRepository
import com.example.domain.model.Result

class GetUserByIdUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(id: Int): Result<User, UserFetchException> {
        return try {
            val user = userRepository.getUser(id)
            Result.Success(user)
        } catch (e: UserFetchException) {
            Result.Error(e)
        }
    }
}