package com.example.twitchstream.api

import com.example.twitchstream.api.response.TopGameResponse
import com.example.twitchstream.api.response.TopVideosResponse
import com.example.twitchstream.db.entity.TopVideo
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRequests {
    @Headers(
        "Accept: application/vnd.twitchtv.v5+json",
        "Client-ID: sd4grh0omdj9a31exnpikhrmsu3v46"
    )
    @GET("games/top")
    fun topGames(
        @Query("offset") offset: Int = 0
    ): Single<TopGameResponse>

    @Headers(
        "Accept: application/vnd.twitchtv.v5+json",
        "Client-ID: sd4grh0omdj9a31exnpikhrmsu3v46"
    )
    @GET("videos/top")
    fun topVideos(
        @Query("game") game: String = ""
    ): Single<TopVideosResponse>

    @Headers(
        "Accept: application/vnd.twitchtv.v5+json",
        "Client-ID: sd4grh0omdj9a31exnpikhrmsu3v46"
    )
    @GET("videos/{id}")
    fun getVideo(
        @Path("id") id: String = ""
    ): Single<TopVideo>
}