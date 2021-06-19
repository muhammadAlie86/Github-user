package com.example.githubuser.api

import com.example.githubuser.api.Constant.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    private val client = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }
        .callTimeout(5, TimeUnit.MINUTES)
        .connectTimeout(5,TimeUnit.MINUTES)
        .build()


    private val retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val apiClient : ApiClient by lazy {
        retrofit.create(ApiClient::class.java)
    }
}