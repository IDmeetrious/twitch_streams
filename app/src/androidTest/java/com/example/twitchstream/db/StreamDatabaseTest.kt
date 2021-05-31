package com.example.twitchstream.db

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.twitchstream.db.entity.Box
import com.example.twitchstream.db.entity.Game
import com.example.twitchstream.db.entity.Logo
import com.example.twitchstream.db.entity.TopGame
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

private const val TAG = "StreamDatabaseTest"
@RunWith(AndroidJUnit4::class)
class StreamDatabaseTest : TestCase() {
    private lateinit var db: StreamDatabase
    private lateinit var dao: StreamDao

    @Before
    public override fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, StreamDatabase::class.java).build()
        dao = db.streamDao()
    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    fun writeAndReadStream(){
        val topGame = TopGame(1, game = Game(1, box = Box("game1"), logo = Logo(gameName = "game1")))
        dao.insertAll(topGame)
        val topGames = dao.getAll()
        print("${topGames[0]}")
        assertTrue(topGames[0].id == topGame.id)
    }
}