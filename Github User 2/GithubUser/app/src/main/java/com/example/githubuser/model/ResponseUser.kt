package com.example.githubuser.model

import com.google.gson.annotations.SerializedName


data class ResponseUser (

    @SerializedName("total_count")
    val totalCount :Int,

    @SerializedName("incomplete_results")
    val incompleteResults :Boolean,

    @SerializedName("items")
    val items : List<User>
    )