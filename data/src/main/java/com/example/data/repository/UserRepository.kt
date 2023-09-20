package com.example.data.repository

import com.example.data.model.User
import com.example.data.model.exception.UserFetchException

/**
 * Repository interface for managing user data.
 */
interface UserRepository {

    /**
     * Retrieves a list of users.
     *
     * @param forceRemote If true, forces fetching data from a remote source.
     *                    If false, tries to use a local cache when available.
     * @return A list of User objects.
     * @throws [UserFetchException.NetworkException] if a network-related error occurs.
     * @throws [UserFetchException.CacheNotAvailableException] if cached data is requested but not available.
     */
    suspend fun getUsers(forceRemote: Boolean = false): List<User>


    /**
     * Retrieves a user by their unique identifier.
     *
     * @param id The unique identifier of the user.
     * @return The User object with the specified [id].
     * @throws [UserFetchException.NetworkException] if a network-related error occurs.
     * @throws [UserFetchException.NotFoundException] if user by id is not found.
     * @throws [UserFetchException.CacheNotAvailableException] if cached data is requested but not available.
     */
    suspend fun getUser(id: Int): User
}