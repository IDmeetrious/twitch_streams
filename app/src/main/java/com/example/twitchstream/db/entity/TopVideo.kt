package com.example.twitchstream.db.entity

import com.google.gson.annotations.SerializedName

data class TopVideo(
    @SerializedName("_id")
    val id: String = "",
    val title: String = "",
    val views: Int = 0,
    @SerializedName("published_at")
    val publishedAt: String = "",
    @SerializedName("preview")
    val previewUrl: Preview? = null
)
