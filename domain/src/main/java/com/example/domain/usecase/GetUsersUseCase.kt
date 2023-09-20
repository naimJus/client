package com.example.domain.usecase

import com.example.data.model.exception.UserFetchException
import com.example.data.repository.UserRepository
import com.example.domain.model.Result
import com.example.domain.model.UserItem
import com.example.domain.model.toItem
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository) : UseCase<Boolean, Result<List<UserItem>, UserFetchException>> {

    override suspend operator fun invoke(param: Boolean): Result<List<UserItem>, UserFetchException> {
        return try {
            val users = userRepository.getUsers(param).map { user ->
                user.toItem()
            }
            Result.Success(users)
        } catch (e: UserFetchException) {
            Result.Error(e)
        }
    }
}
