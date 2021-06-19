package com.example.consumerapp.repository

import com.example.consumerapp.model.call.RemoteDataSource


class Repository private constructor(
    private val remoteDataSource: RemoteDataSource
) {

    companion object {

        @Volatile
        private var instance: Repository? = null
        fun getInstance(
                remoteDataSource: RemoteDataSource,
        ): Repository =
                instance ?: synchronized(this) {
                    instance ?: Repository(remoteDataSource)
                }
    }

    suspend fun getSearchUser(query : String) = remoteDataSource.getSearchUser(query)
    suspend fun getDetailUser(username : String) = remoteDataSource.getDetailUser(username)
    suspend fun getFollower(username: String,part : String) = remoteDataSource.getFollower(username, part)
    suspend fun getFollowing(username: String,part : String) = remoteDataSource.getFollowing(username, part)


}