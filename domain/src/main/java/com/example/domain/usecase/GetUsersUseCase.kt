package com.example.domain.usecase

import com.example.data.model.User
import com.example.data.model.exception.UserFetchException
import com.example.data.repository.UserRepository
import com.example.domain.model.Result
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) : UseCase<Boolean, Result<List<User>, UserFetchException>> {

    override suspend operator fun invoke(param: Boolean): Result<List<User>, UserFetchException> {
        return try {
            val users = userRepository.getUsers(param)
            Result.Success(users)
        } catch (e: UserFetchException) {
            Result.Error(e)
        }
    }
}
