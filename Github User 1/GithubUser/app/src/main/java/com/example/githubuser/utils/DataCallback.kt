package com.example.githubuser.utils

import com.example.githubuser.model.User

interface DataCallback {

    fun setData(user: User)
}