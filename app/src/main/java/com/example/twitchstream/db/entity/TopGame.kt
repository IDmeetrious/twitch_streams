package com.example.twitchstream.db.entity

import androidx.room.*

@Entity(tableName = "topGame")
data class TopGame(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val channels: Int = 0,
    val viewers: Int = 0,
    val game: Game
)
