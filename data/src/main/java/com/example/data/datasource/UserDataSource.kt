package com.example.data.datasource

import com.example.data.model.User

/**
 * Interface representing a User Data Source for retrieving user data from different sources (remote, local db, etc).
 */
interface UserDataSource {

    /**
     * Retrieves a list of users from the data source.
     *
     * @return A list of User objects representing the users from the data source.
     */
    suspend fun getUsers(): List<User>
}

