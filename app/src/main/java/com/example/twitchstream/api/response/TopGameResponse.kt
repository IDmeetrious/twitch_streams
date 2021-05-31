package com.example.twitchstream.api.response

import com.example.twitchstream.db.entity.TopGame

data class TopGameResponse(
    val _total: Int = 1,
    val top: List<TopGame> = emptyList()
)
