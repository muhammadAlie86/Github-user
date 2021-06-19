package com.example.githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubuser.database.UserContract
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.githubuser.database.UserHelper

class UserProvider : ContentProvider() {

    companion object{
        private const val USER = 1
        private const val USER_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var userHelper: UserHelper

        init {
            sUriMatcher.addURI(UserContract.AUTHORITY, UserContract.FavoriteColumns.TABLE_NAME, USER)
            sUriMatcher.addURI(UserContract.AUTHORITY, "${UserContract.FavoriteColumns.TABLE_NAME}/#", USER_ID)
        }
    }
    override fun onCreate(): Boolean {
        userHelper = UserHelper.getInstance(context as Context)
        userHelper.open()
        return true
    }

    override fun getType(uri: Uri): String? {
        return null
    }
    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)){
        USER -> userHelper.queryAll()
        USER_ID -> userHelper.queryById()
        else -> null
    }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added : Long = when(USER){
            sUriMatcher.match(uri) -> userHelper.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI,null)

        return Uri.parse("$CONTENT_URI/$added")
    }


    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (USER_ID) {
            sUriMatcher.match(uri) -> userHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }
}