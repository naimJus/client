package com.example.domain.usecase

import com.example.data.model.exception.UserFetchException
import com.example.data.repository.UserRepository
import com.example.domain.model.Result
import com.example.domain.model.UserItem
import com.example.domain.model.toItem
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(private val userRepository: UserRepository) : UseCase<Long, Result<UserItem, UserFetchException>> {

    override suspend operator fun invoke(param: Long): Result<UserItem, UserFetchException> {
        return try {
            val user = userRepository.getUser(param)
            Result.Success(user.toItem())
        } catch (e: UserFetchException) {
            Result.Error(e)
        }
    }
}