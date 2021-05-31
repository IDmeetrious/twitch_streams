package com.example.twitchstream.api

import com.example.twitchstream.api.response.TopGameResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiRequests {
    @Headers(
        "Accept: application/vnd.twitchtv.v5+json",
        "Client-ID: sd4grh0omdj9a31exnpikhrmsu3v46"
    )
    @GET("games/top")
    fun topGames(
        @Query("offset") offset: Int = 0
    ): Call<TopGameResponse>
}