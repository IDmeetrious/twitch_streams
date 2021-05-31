package com.example.twitchstream.api.response.model

data class TopGame(
    val channels: Int = 0,
    val viewers: Int = 0,
    val game: Game? = null
)