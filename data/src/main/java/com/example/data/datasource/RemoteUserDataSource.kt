package com.example.data.datasource

import com.example.data.model.User
import com.example.data.api.UserApi

internal class RemoteUserDataSource(private val api: UserApi) : UserDataSource {

    override fun getUsers(): List<User> {
        return api.getUser()
    }
}