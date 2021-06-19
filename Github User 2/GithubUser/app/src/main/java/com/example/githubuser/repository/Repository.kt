package com.example.githubuser.repository

import com.example.githubuser.api.RetrofitInstance

class Repository {
    suspend fun getSearchUser(query : String) = RetrofitInstance.apiClient.getSearchUser(query)
    suspend fun getDetailUser(username : String) = RetrofitInstance.apiClient.getDetailUser(username)
    suspend fun getFollower(username: String,part : String) = RetrofitInstance.apiClient.getFollower(username, part)
    suspend fun getFollowing(username: String,part : String) = RetrofitInstance.apiClient.getFollowing(username, part)
}