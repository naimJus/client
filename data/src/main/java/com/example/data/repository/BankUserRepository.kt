package com.example.data.repository

import com.example.data.datasource.UserDataSource
import com.example.data.model.User
import com.example.data.model.exception.UserFetchException

internal class BankUserRepository(
    private val remoteDataSource: UserDataSource
) : UserRepository {

    private val cachedUsers = mutableListOf<User>()

    @Throws(
        UserFetchException.NetworkException::class,
        UserFetchException.CacheNotAvailableException::class
    )
    override suspend fun getUsers(forceRemote: Boolean): List<User> {
        return if (forceRemote) {
            getRemoteUsers()
        } else {
            getCachedUsers()
        }
    }

    @Throws(
        UserFetchException.NetworkException::class,
        UserFetchException.NotFoundException::class,
        UserFetchException.CacheNotAvailableException::class
    )
    override suspend fun getUser(id: Int): User {
        val user = cachedUsers.firstOrNull { it.id == id }
        return user ?: throw UserFetchException.NotFoundException(id)
    }

    @Throws(UserFetchException.NetworkException::class)
    private fun getRemoteUsers(): List<User> {
        try {
            val users = remoteDataSource.getUsers()
            cachedUsers.clear()
            cachedUsers.addAll(users)
            return users
        } catch (e: Exception) {
            throw UserFetchException.NetworkException
        }
    }

    @Throws(UserFetchException.CacheNotAvailableException::class)
    private fun getCachedUsers(): List<User> {
        return if (cachedUsers.isEmpty()) {
            throw UserFetchException.CacheNotAvailableException
        } else {
            cachedUsers
        }
    }

}