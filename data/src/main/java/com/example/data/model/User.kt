package com.example.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String? = null,
    @SerializedName("email") val email: String? = null,
    @SerializedName("address") val address: Address? = null,
    @SerializedName("phone") val phone: String? = null,
    @SerializedName("website") val website: String? = null,
    @SerializedName("company") val company: Company? = null
)