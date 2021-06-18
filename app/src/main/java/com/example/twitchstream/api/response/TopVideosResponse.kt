package com.example.twitchstream.api.response

import com.example.twitchstream.db.entity.TopVideo

data class TopVideosResponse(
    val vods: List<TopVideo> = emptyList()
)