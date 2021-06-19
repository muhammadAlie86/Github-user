package com.example.githubuser.api

import com.example.githubuser.model.entity.DetailUser
import com.example.githubuser.model.entity.ResponseUser
import com.example.githubuser.model.entity.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {


    @GET("search/users?")
    suspend fun getSearchUser(@Query("q") query : String) : Response<ResponseUser>

    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username") username : String): Response<DetailUser>

    @GET("users/{username}/followers")
    suspend fun getFollower(@Path("username") username: String,
                    @Query("part") part : String) : Response<List<User>>

    @GET("users/{username}/following")
    suspend fun getFollowing(@Path("username") username: String,
                     @Query("part") part : String) : Response<List<User>>
}