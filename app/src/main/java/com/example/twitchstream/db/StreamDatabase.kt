package com.example.twitchstream.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import com.example.twitchstream.db.converter.GameConverter
import com.example.twitchstream.db.entity.TopGame

@Database(entities = [TopGame::class], version = 2)
@TypeConverters(GameConverter::class)
abstract class StreamDatabase : RoomDatabase() {
    abstract fun streamDao(): StreamDao
    companion object{
        @Volatile
        private var instance: StreamDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            StreamDatabase::class.java,
            "stream_database"
        ).fallbackToDestructiveMigration()
            .build()
    }
}