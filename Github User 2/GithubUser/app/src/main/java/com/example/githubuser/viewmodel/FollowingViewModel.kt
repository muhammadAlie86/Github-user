package com.example.githubuser.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.model.User
import com.example.githubuser.repository.Repository
import com.example.githubuser.vo.Resource
import kotlinx.coroutines.launch

class FollowingViewModel : ViewModel() {

    private val repository = Repository()

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