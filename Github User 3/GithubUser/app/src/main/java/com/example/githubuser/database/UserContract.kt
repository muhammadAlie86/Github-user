package com.example.githubuser.database

import android.net.Uri
import android.provider.BaseColumns

object UserContract {

    const val AUTHORITY ="com.example.githubuser"
    const val SCHEME = "content"

    class FavoriteColumns : BaseColumns {

        companion object{

            const val TABLE_NAME = "user"
            const val ID = "id"
            const val USERNAME = "username"
            const val IMG_FAVORITE = "img_favorite"
            const val GITHUB = "github"

            val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(TABLE_NAME)
                    .build()

        }

    }
}