package com.example.data.api

import com.example.data.model.User
import com.example.data.util.USERS_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET

/**
 * Interface representing a User API for retrieving user data.
 */
internal interface UserApi {

    /**
     * Fetches a list of users from the specified API endpoint.
     *
     * @return A list of User objects representing the users retrieved from the API.
     */
    @GET(USERS_ENDPOINT)
    suspend fun getUser(): Response<List<User>>
}