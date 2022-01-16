package com.roy.githubuser.dataclass

data class UserResponse(

    @field:SerializedName("items")
    val items: ArrayList<User>
)

annotation class SerializedName(val value: String)
