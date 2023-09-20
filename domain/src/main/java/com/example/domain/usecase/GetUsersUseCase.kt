package com.example.domain.usecase

import com.example.data.model.User
import com.example.data.model.exception.UserFetchException
import com.example.data.repository.UserRepository
import com.example.domain.model.Result

class GetUsersUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(forceRemote: Boolean = false): Result<List<User>, UserFetchException> {
        return try {
            val users = userRepository.getUsers(forceRemote)
            Result.Success(users)
        } catch (e: UserFetchException) {
            Result.Error(e)
        }
    }
}
