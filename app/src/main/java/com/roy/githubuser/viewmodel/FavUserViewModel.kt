package com.roy.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.roy.githubuser.db.local.FavoriteUser
import com.roy.githubuser.db.local.FavoriteUserDao
import com.roy.githubuser.db.local.UserDatabase

class FavUserViewModel(application: Application): AndroidViewModel(application) {
    private var userDao: FavoriteUserDao
    private var userDb: UserDatabase = UserDatabase.getDatabase(application)

    init {
        userDao = userDb.favoriteUserDao()
    }

    fun getFavoriteUser(): LiveData<List<FavoriteUser>> {
        return userDao.getFavoriteUser()
    }
}