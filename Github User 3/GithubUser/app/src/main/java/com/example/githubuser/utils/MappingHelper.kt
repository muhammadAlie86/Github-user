package com.example.githubuser.utils

import android.database.Cursor
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.GITHUB
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.ID
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.IMG_FAVORITE
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.USERNAME
import com.example.githubuser.model.entity.User

object MappingHelper {

    fun mapCursorToArrayList(favoriteCursor: Cursor?): List<User> {
        val favoriteList = ArrayList<User>()
        favoriteCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(ID))
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val avatarUrl = getString(getColumnIndexOrThrow(IMG_FAVORITE))
                val github = getString(getColumnIndexOrThrow(GITHUB))
                favoriteList.add(User(id,username,avatarUrl,github))
            }
        }
        return favoriteList
    }
}