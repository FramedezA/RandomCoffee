package com.example.randomcoffee.services.Retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofit: Retrofit? = null
    private const val baseUrl = "https://67d76641-9cc3-4615-b828-740d49a56ba9-00-19gb8a6nrlnf0.riker.replit.dev:8080/"

    fun getClient(): Retrofit {
        val gsonBuilder = GsonBuilder().setLenient().create()

        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder))
                .build()
        }
        return retrofit!!
    }

}