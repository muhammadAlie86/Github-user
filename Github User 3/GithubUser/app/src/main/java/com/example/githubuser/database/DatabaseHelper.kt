package com.example.githubuser.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.GITHUB
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.ID
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.IMG_FAVORITE
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.TABLE_NAME
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.USERNAME

class DatabaseHelper (context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "favorite.db"

        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE_USER = "CREATE TABLE $TABLE_NAME" +
                " ($ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $USERNAME TEXT NOT NULL," +
                " $IMG_FAVORITE TEXT NOT NULL," +
                " $GITHUB TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}