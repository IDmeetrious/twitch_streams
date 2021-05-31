package com.example.twitchstream.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.twitchstream.db.converter.LogoConverter

@Entity(tableName = "logo")
@TypeConverters(LogoConverter::class)
data class Logo(
    val gameName: String = "",
    val large: String = "",
    val medium: String = "",
    var small: String = "",
    val template: String = ""
)