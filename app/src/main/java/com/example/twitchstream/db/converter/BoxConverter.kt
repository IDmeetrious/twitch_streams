package com.example.twitchstream.db.converter

import androidx.room.TypeConverter
import com.example.twitchstream.db.entity.Box
import com.example.twitchstream.db.entity.Game
import java.util.*

class BoxConverter {
    @TypeConverter
    fun fromModel(box: Box): String{
        return box.toString()
    }

    @TypeConverter
    fun toModel(string: String): Box{
        /** Created by ID
         * date: 02-Jun-21, 7:38 AM
         * TODO: create model associate with string
         */
        return Box()
    }
}