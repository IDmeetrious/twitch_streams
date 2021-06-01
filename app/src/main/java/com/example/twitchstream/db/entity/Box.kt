package com.example.twitchstream.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.twitchstream.db.converter.BoxConverter

@Entity(tableName = "box")
@TypeConverters(BoxConverter::class)
data class Box(
    @ColumnInfo(name = "largeBox")
    val large: String = "",
    @ColumnInfo(name = "mediumBox")
    val medium: String = "",
    @ColumnInfo(name = "smallBox")
    val small: String = "",
    @ColumnInfo(name = "templateBox")
    val template: String = ""
)
