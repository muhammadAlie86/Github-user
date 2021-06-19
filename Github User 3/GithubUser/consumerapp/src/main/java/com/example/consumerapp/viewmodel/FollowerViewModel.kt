package com.example.consumerapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consumerapp.model.entity.User
import com.example.consumerapp.repository.Repository
import com.example.consumerapp.vo.Resource
import kotlinx.coroutines.launch

class FollowerViewModel (private val repository : Repository) : ViewModel() {


    private val _listFollower : MutableLiveData<Resource<List<User>?>> = MutableLiveData()
    val listFollower : LiveData<Resource<List<User>?>>
    get() = _listFollower

    fun getFollower(username : String,part : String) = viewModelScope.launch {

        val response = repository.getFollower(username,part)
        val body = response.body()
        try {
            if (response.isSuccessful) {
                _listFollower.postValue(Resource.Success(body))

            }
        } catch (e: Exception) {
            _listFollower.postValue(e.message?.let { Resource.Error(it) })
        }
    }
}