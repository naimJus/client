package com.example.data.repository

import com.example.data.datasource.UserDataSource
import com.example.data.model.User
import com.example.data.model.exception.UserFetchException
import org.jetbrains.annotations.VisibleForTesting
import java.net.UnknownHostException
import javax.inject.Inject

internal class BankUserRepository @Inject constructor(private val remoteDataSource: UserDataSource) : UserRepository {

    @VisibleForTesting
    internal val cachedUsers = mutableListOf<User>()

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
    override suspend fun getUser(id: Long): User {
        val user = cachedUsers.firstOrNull { it.id == id }
        return user ?: throw UserFetchException.NotFoundException(id)
    }

    @Throws(UserFetchException.NetworkException::class)
    private suspend fun getRemoteUsers(): List<User> {
        try {
            val users = remoteDataSource.getUsers()
            cachedUsers.clear()
            cachedUsers.addAll(users)
            return users
        } catch (e: UnknownHostException) {
            throw UserFetchException.NetworkException
        } catch (e: Exception) {
            throw UserFetchException.UnknownException(e, e.localizedMessage)
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