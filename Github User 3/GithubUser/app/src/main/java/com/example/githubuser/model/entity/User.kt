package com.example.githubuser.model.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
        @SerializedName("id")
        val id: Int,

        @SerializedName("login")
        val name: String,

        @SerializedName("html_url")
        val github: String,

        @SerializedName("avatar_url")
        val avatarUrl: String,


) : Parcelable