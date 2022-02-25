package com.roy.githubuser.api

data class UserResponse(

    @field:SerializedName("items")
    val items: ArrayList<User>
)

annotation class SerializedName(val value: String)
