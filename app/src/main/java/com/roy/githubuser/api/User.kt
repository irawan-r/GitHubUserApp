package com.roy.githubuser.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int = 0,
    val login: String? = null,
    val avatar_url: String? = null,
    val user_url: String? = null,
    val name: String? = null,
    val company: String? = null,
    val location: String? = null,
    val public_repos: Int = 0,
    val followers: Int = 0,
    val following: Int = 0
) : Parcelable
