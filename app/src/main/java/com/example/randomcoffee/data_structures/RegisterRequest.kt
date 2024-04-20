package com.example.randomcoffee.data_structures

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @field:SerializedName("email") val email: String,
    @field:SerializedName("password") val password: String
)
