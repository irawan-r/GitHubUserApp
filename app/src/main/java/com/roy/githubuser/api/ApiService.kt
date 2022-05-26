package com.roy.githubuser.api

import com.roy.githubuser.BuildConfig
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token $GITHUB_TOKEN")
    fun getSearchUser(@Query("q") query: String): Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $GITHUB_TOKEN")
    fun getUserDetail(
        @Path("username") username: String?
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $GITHUB_TOKEN")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $GITHUB_TOKEN")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}

const val GITHUB_TOKEN: String = BuildConfig.GITHUB_TOKEN

