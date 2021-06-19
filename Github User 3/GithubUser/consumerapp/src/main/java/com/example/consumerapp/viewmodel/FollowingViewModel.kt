package com.example.consumerapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consumerapp.model.entity.User
import com.example.consumerapp.repository.Repository
import com.example.consumerapp.vo.Resource
import kotlinx.coroutines.launch

class FollowingViewModel (private val repository : Repository): ViewModel() {


    private val _listFollowing : MutableLiveData<Resource<List<User>?>> = MutableLiveData()
    val listFollowing : LiveData<Resource<List<User>?>>
    get() = _listFollowing

    fun getFollowing(username : String,part : String) = viewModelScope.launch {

        val response = repository.getFollowing(username,part)
        val body = response.body()
        try {
            if (response.isSuccessful) {
                _listFollowing.postValue(Resource.Success(body))

            }
        } catch (e: Exception) {
            _listFollowing.postValue(e.message?.let { Resource.Error(it) })
        }
    }
}