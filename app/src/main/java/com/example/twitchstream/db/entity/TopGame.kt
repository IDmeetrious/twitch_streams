package com.example.twitchstream.db.entity

import androidx.room.*

@Entity(tableName = "topGame")
data class TopGame(
    @PrimaryKey(autoGenerate = true)
    val gameId: Int,
    val channels: Int,
    val viewers: Int,
    @Embedded
    val game: Game
)
