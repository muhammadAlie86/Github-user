package com.example.githubuser.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.ID
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.TABLE_NAME
import java.sql.SQLException

class UserHelper (context: Context) {
    companion object{
        private const val DATABASE_TABLE = TABLE_NAME
        private lateinit var databaseHelper: DatabaseHelper
        private lateinit var database : SQLiteDatabase
        private var INSTANCE : UserHelper? = null

        fun getInstance(context: Context) : UserHelper =
                INSTANCE ?: synchronized(this){
                    INSTANCE ?: UserHelper(context)
                }
    }
    init {
        databaseHelper = DatabaseHelper(context)
    }
    @Throws(SQLException::class)
    fun open(){
        database  = databaseHelper.writableDatabase
    }

    fun queryAll() : Cursor{
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                "$ID ASC"
        )
    }
    fun queryById () : Cursor {

        return  database.query(
                DATABASE_TABLE,
                null,
                "ID = ?" ,
                arrayOf(ID),
                null,
                null,
                null,
                null
        )
    }
    fun insert(values: ContentValues?) : Long{
        return database.insert(DATABASE_TABLE,null,values)
    }
    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$ID = '$id'", null)
    }

    fun checked (id : String) : Boolean {
        database = databaseHelper.writableDatabase
        val querySelect = "SELECT * FROM $DATABASE_TABLE WHERE $ID = ?"
        val result = database.rawQuery(querySelect, arrayOf(id))
        var checkStatus = false
        var i = 0
        do {
            if (result.moveToFirst()) {
                checkStatus = !checkStatus
            }
            i++
        } while (result.moveToNext())


        result.close()
        return checkStatus
    }
}

