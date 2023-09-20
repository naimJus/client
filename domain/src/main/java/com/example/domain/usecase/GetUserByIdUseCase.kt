package com.example.domain.usecase

import com.example.data.model.User
import com.example.data.model.exception.UserFetchException
import com.example.data.repository.UserRepository
import com.example.domain.model.Result
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(private val userRepository: UserRepository) : UseCase<Int, Result<User, UserFetchException>> {

    override suspend operator fun invoke(param: Int): Result<User, UserFetchException> {
        return try {
            val user = userRepository.getUser(param)
            Result.Success(user)
        } catch (e: UserFetchException) {
            Result.Error(e)
        }
    }
}