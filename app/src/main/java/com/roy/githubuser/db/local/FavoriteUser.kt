package com.roy.githubuser.db.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity(tableName = "favorite_user")
@Parcelize
data class FavoriteUser(
    val login: String,
    @PrimaryKey
    val id: Int,
    val avatar_url: String
    ): Serializable, Parcelable