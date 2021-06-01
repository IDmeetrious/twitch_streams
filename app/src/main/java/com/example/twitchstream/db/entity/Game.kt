package com.example.twitchstream.db.entity

import androidx.room.*
import com.example.twitchstream.db.converter.GameConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "game")
@TypeConverters(GameConverter::class)
class Game(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    val id: Int,
    @Embedded
    val box: Box,
    @ColumnInfo(name = "giantbomb_id")
    val giantbombId: Int,
    @Embedded
    val logo: Logo,
    val name: String
)
