package com.example.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.model.DetailUser
import com.example.githubuser.repository.Repository
import com.example.githubuser.vo.Resource
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private val repository = Repository()
    private val _detailUser : MutableLiveData<Resource<DetailUser>?> = MutableLiveData()
    val detailUser : LiveData<Resource<DetailUser>?>
    get() = _detailUser


    fun getDetailUser(username : String) = viewModelScope.launch {

        val response = repository.getDetailUser(username)
        try {
            if (response.isSuccessful) {
                _detailUser.postValue( response.body()?.let { Resource.Success(it) })
            }
        } catch (e: Exception) {
            _detailUser.postValue(e.message?.let { Resource.Error(it) })
        }
    }


}