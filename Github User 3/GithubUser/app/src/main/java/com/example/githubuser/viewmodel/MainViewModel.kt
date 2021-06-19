package com.example.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.model.entity.User
import com.example.githubuser.repository.Repository
import com.example.githubuser.vo.Resource
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {


    private val _listUser : MutableLiveData<Resource<List<User>?>> = MutableLiveData()
    val listUser : LiveData<Resource<List<User>?>>
    get() = _listUser


    fun getSearchUser(query : String) = viewModelScope.launch {

        val response = repository.getSearchUser(query)
        val body = response.body()?.items
        try {
            if (response.isSuccessful) {
                _listUser.postValue(Resource.Success(body))

            }
        } catch (e: Exception) {
            _listUser.postValue(e.message?.let { Resource.Error(it) })
        }
    }
}




