package com.example.twitchstream.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.twitchstream.db.converter.BoxConverter

@Entity(tableName = "box")
@TypeConverters(BoxConverter::class)
data class Box(
    val gameName: String = "",
    val large: String = "",
    val medium: String = "",
    val small: String = "",
    val template: String =""
)
