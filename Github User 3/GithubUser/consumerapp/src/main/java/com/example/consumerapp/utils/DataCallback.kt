package com.example.consumerapp.utils

import com.example.consumerapp.model.entity.User


interface DataCallback {

    fun setData(user: User)
}