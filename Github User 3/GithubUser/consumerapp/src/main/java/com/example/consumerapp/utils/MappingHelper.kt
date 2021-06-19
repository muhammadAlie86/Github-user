package com.example.consumerapp.utils

import android.database.Cursor
import com.example.consumerapp.database.UserContract.FavoriteColumn.Companion.GITHUB
import com.example.consumerapp.database.UserContract.FavoriteColumn.Companion.ID
import com.example.consumerapp.database.UserContract.FavoriteColumn.Companion.IMG_FAVORITE
import com.example.consumerapp.database.UserContract.FavoriteColumn.Companion.USERNAME
import com.example.consumerapp.model.entity.User


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