package com.example.domain.model

import com.example.data.model.User

data class UserItem(
    val id: Long,
    val name: String,
    val userName: String,
    val email: String,
    val phone: String,
    val address: String,
    val company: String
)

internal fun User.toItem(): UserItem {
    val name = this.name
    val userName = this.username.orEmpty()
    val email = this.email.orEmpty()
    val phone = this.phone.orEmpty()
    val company = this.company?.name.orEmpty()
    val address = this.address?.let { "${it.street}\n${it.zipcode}, ${it.city}" }.orEmpty()

    return UserItem(this.id, name, userName, email, phone, address, company)
}
