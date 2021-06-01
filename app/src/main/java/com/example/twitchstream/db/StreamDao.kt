package com.example.twitchstream.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.twitchstream.db.entity.TopGame

@Dao
interface StreamDao {
    @Query("SELECT * FROM topGame")
    fun getAll(): List<TopGame>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg topGames: TopGame)
}
