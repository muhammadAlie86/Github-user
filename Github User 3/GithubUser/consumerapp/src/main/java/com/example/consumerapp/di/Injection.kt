package com.example.consumerapp.di

import android.content.Context
import com.example.consumerapp.model.call.RemoteDataSource
import com.example.consumerapp.repository.Repository


object Injection {

    fun provideRepository(context: Context): Repository {


        val remoteDataSource = RemoteDataSource.getInstance()


        return Repository.getInstance(remoteDataSource)
    }
}