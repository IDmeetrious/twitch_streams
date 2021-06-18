package com.example.twitchstream.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.twitch.tv/kraken/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api = retrofit.create(ApiRequests::class.java)
}