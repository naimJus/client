package com.example.data.datasource

import com.example.data.model.User
import com.example.data.api.UserApi
import javax.inject.Inject

internal class RemoteUserDataSource @Inject constructor(private val api: UserApi) : UserDataSource {

    override suspend fun getUsers(): List<User> {
        return api.getUser()
    }
}