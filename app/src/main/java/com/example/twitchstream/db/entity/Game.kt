package com.example.twitchstream.db.entity

import androidx.room.*
import com.example.twitchstream.db.converter.GameConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "game")
@TypeConverters(GameConverter::class)
class Game(
    @PrimaryKey
    @ColumnInfo(name = "_id")
    val id: Int = 0,
    val box: Box? = null,
    @ColumnInfo(name = "giantbomb_id")
    val giantbombId: Int = 0,
    /** Created by ID
     * date: 26-May-21, 5:19 PM
     * TODO: should save images
     */
    val logo: Logo? = null,
    val name: String = ""
)
