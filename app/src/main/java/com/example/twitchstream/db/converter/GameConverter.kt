package com.example.twitchstream.db.converter

import androidx.room.TypeConverter
import com.example.twitchstream.db.entity.Box
import com.example.twitchstream.db.entity.Game
import com.example.twitchstream.db.entity.Logo

class GameConverter {
    @TypeConverter
    fun fromModel(game: Game): String{
        return game.name
    }

    @TypeConverter
    fun toModel(string: String): Game{
        return Game(
            id = 0,
            name = string,
            logo = Logo(),
            box = Box(),
            giantBombId = 0)
    }
}