package com.example.lab_1.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SentinelRetrofitClient {

    val api: SentinelGitHubApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SentinelGitHubApi::class.java)
    }
}