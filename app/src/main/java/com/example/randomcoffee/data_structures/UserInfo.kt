package com.example.randomcoffee.data_structures

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("login_status")
    val loginStatusCode: Int,
    @SerializedName("username")
    val userName: String,
    @SerializedName("user_id")
    val userId: Int

)
