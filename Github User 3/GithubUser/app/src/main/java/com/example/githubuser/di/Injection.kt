package com.example.githubuser.di

import android.content.Context
import com.example.githubuser.model.call.RemoteDataSource
import com.example.githubuser.repository.Repository

object Injection {

    fun provideRepository(context: Context): Repository {

        val remoteDataSource = RemoteDataSource.getInstance()


        return Repository.getInstance(remoteDataSource)
    }
}