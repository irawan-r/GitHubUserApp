package com.roy.githubuser.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.roy.githubuser.api.ApiConfig
import com.roy.githubuser.dataclass.DetailUserResponse
import com.roy.githubuser.db.local.FavoriteUser
import com.roy.githubuser.db.local.FavoriteUserDao
import com.roy.githubuser.db.local.UserDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : AndroidViewModel(application) {
    val user = MutableLiveData<DetailUserResponse>()

    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabase? = UserDatabase.getDatabase(application)

    init {
        userDao = userDb?.favoriteUserDao()
    }

    fun setUserDetail(username: String?) {
        val client = ApiConfig.getApiService().getUserDetail(username)

        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>,
            ) {
                if (response.isSuccessful) {
                    user.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.d("Failure", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getUserDetail(): LiveData<DetailUserResponse> = user

    fun addToFavorite(login: String, position: Int, avatarUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = FavoriteUser(
                login, position, avatarUrl
            )
            userDao?.addToFavorite(user)
        }
    }

    fun checkUser(id: Int) = userDao?.checkUser(id)

    fun removeFromFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.removeFromFavorite(id)
        }
    }
}