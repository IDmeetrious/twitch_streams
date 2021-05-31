package com.example.twitchstream.db.converter

import androidx.room.TypeConverter
import com.example.twitchstream.db.entity.Box
import com.example.twitchstream.db.entity.Game
import java.util.*

class BoxConverter {
    @TypeConverter
    fun fromModel(box: Box): String{
        return box.gameName
    }

    @TypeConverter
    fun toModel(string: String): Box{
        return Box(gameName = string)
    }
}