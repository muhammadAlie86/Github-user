package com.example.consumerapp.model.entity

import com.google.gson.annotations.SerializedName


data class DetailUser (

    @SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @SerializedName("login")
    val username: String? = null,

    @SerializedName("company")
    val company: String?= null,

    @SerializedName("location")
    val location: String? = null,

    @SerializedName("html_url")
    val github: String,

    @SerializedName("followers")
    val followers: Int? = null,

    @SerializedName("following")
    val following: Int? = null,

    @SerializedName("public_repos")
    val repo: Int? = null
)