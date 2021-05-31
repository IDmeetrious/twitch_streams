package com.example.twitchstream.db

import android.content.Context
import androidx.room.Room

class StreamDbClient(private val context: Context) {
    val db = Room.databaseBuilder(context, StreamDatabase::class.java, "stream_database")
}