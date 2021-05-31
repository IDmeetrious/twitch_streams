package com.example.twitchstream.db.converter

import androidx.room.TypeConverter
import com.example.twitchstream.db.entity.Game
import com.example.twitchstream.db.entity.Logo
import java.util.*

class LogoConverter {
    @TypeConverter
    fun fromModel(logo: Logo): String{
        return logo.gameName
    }

    @TypeConverter
    fun toModel(string: String): Logo{
        return Logo(gameName = string)
    }
}