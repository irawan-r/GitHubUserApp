package com.roy.githubuser.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.roy.githubuser.api.ApiConfig
import com.roy.githubuser.dataclass.User
import com.roy.githubuser.dataclass.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val user = MutableLiveData<ArrayList<User>>()

    fun setSearchUser(query: String) {
        val client = ApiConfig.getApiService().getSearchUser(query)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>,
            ) {
                if (response.isSuccessful) {
                    user.postValue(response.body()?.items)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.d("Failure", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getUserName(): LiveData<ArrayList<User>> {
        return user
    }
}



