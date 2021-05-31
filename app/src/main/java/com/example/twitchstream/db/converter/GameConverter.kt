package com.example.twitchstream.db.converter

import androidx.room.TypeConverter
import com.example.twitchstream.db.entity.Game
import java.util.*

class GameConverter {
    @TypeConverter
    fun fromModel(game: Game): String{
        return game.name
    }

    @TypeConverter
    fun toModel(string: String): Game{
        return Game(name = string)
    }
}