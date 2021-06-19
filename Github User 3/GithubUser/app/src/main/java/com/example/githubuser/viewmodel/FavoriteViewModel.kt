package com.example.githubuser.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.database.UserContract.FavoriteColumns.Companion.CONTENT_URI
import com.example.githubuser.model.entity.User
import com.example.githubuser.utils.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class FavoriteViewModel : ViewModel(){

    private val _queryAll : MutableLiveData<List<User>?> = MutableLiveData()
    val queryAll : LiveData<List<User>?>
        get() = _queryAll



    fun getQueryAll(context: Context) = viewModelScope.launch(Dispatchers.Main){
        val deferredQueryAll = async (Dispatchers.IO){
            val cursor = context.contentResolver.query(CONTENT_URI, null, null, null, null)
            MappingHelper.mapCursorToArrayList(cursor)
        }
        val getData = deferredQueryAll.await()
        _queryAll.postValue(getData)
    }

}