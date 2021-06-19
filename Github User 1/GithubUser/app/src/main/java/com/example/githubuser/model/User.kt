package com.example.githubuser.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val name : String,
    val location : String,
    val company : String,
    val repository : Int,
    val follower : Int,
    val following : Int,
    val image : Int
    ) : Parcelable