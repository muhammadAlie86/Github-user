package com.example.consumerapp.model.call

import com.example.consumerapp.api.RetrofitInstance


class RemoteDataSource {

    companion object {

    @Volatile
    private var instance: RemoteDataSource? = null

    fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    suspend fun getSearchUser(query : String) = RetrofitInstance.apiClient.getSearchUser(query)
    suspend fun getDetailUser(username : String) = RetrofitInstance.apiClient.getDetailUser(username)
    suspend fun getFollower(username: String,part : String) = RetrofitInstance.apiClient.getFollower(username, part)
    suspend fun getFollowing(username: String,part : String) = RetrofitInstance.apiClient.getFollowing(username, part)
}