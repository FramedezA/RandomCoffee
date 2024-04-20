package com.example.randomcoffee.services.Retrofit

import com.example.randomcoffee.services.Retrofit.interfaces.UserRetrofitService

object RetrofitServiceProvider {

    val userRetrofitService: UserRetrofitService by lazy {
        RetrofitClient.getClient().create(UserRetrofitService::class.java)
    }
}