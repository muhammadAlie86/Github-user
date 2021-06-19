package com.example.githubuser.utils

import com.example.githubuser.model.entity.User

interface DataCallback {

    fun setData(user: User)
}