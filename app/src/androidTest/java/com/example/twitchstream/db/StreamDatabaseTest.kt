package com.example.twitchstream.db

import android.content.Context
import android.util.Base64
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
import java.net.URL

private const val TAG = "StreamDatabaseTest"

@RunWith(AndroidJUnit4::class)
class StreamDatabaseTest : TestCase() {
    private lateinit var db: StreamDatabase
    private lateinit var dao: StreamDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, StreamDatabase::class.java).build()
        dao = db.streamDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndReadStream() {
        val imageUri =
            "https://images.unsplash.com/photo-1461988320302-91bde64fc8e4?ixid=2yJhcHBfaWQiOjEyMDd9&&fm=jpg&w=400&fit=max"
        val incomeImg = URL(imageUri).openStream()

        val byteImg = incomeImg.readBytes()
        val encodedImg = Base64.encodeToString(byteImg, Base64.DEFAULT)
        Log.i(TAG, "--> writeAndReadStream: ${encodedImg}")
        Log.i(TAG, "--> writeAndReadStream: ${encodedImg.length}")
        val logo = Logo(gameName = "game1", small = encodedImg)
        val topGame =
            TopGame(1,
                game = Game(1,
                    box = Box("game1",),
                    logo = logo, giantBombId = 0, name = "Dota2"),
                channels = 1, viewers = 1)
        dao.insertAll(topGame)
        val topGames = dao.getAll()
        Log.i(TAG, "--> writeAndReadStream: ${topGames[0]}")
        Log.i(TAG, "--> writeAndReadStream: ${topGames[0].game}")
        Log.i(TAG, "--> writeAndReadStream: ${topGames[0].game.id}")
        Log.i(TAG, "--> writeAndReadStream: ${topGames[0].game.logo?.small}")

        assertTrue(topGames[0].gameId == topGame.gameId)
    }
}