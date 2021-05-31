package com.example.twitchstream.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.twitch.tv/kraken/")

        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(ApiRequests::class.java)
}