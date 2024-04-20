package com.example.randomcoffee.services.Retrofit.interfaces

import com.example.randomcoffee.data_structures.UserInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserRetrofitService {
    @GET("register/{username}/{email}/{password}")
    fun regNewUser(
        @Path("username") userName: String,
        @Path("email") email: String,
        @Path("password") password: String
    ): Call<String>


    @GET("login/{email}/{password}")
    fun loginUser(
        @Path("email") email: String,
        @Path("password") password: String
    ): Call<UserInfo>
}