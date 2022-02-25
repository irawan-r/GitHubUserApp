package com.roy.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.roy.githubuser.db.model.FavoriteUser
import com.roy.githubuser.db.model.FavoriteUserDao
import com.roy.githubuser.db.model.UserDatabase

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