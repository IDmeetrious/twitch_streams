package com.example.twitchstream.data

import android.content.Context
import android.util.Base64
import com.example.twitchstream.api.ApiRequests
import com.example.twitchstream.api.response.TopGameResponse
import com.example.twitchstream.api.response.TopVideosResponse
import com.example.twitchstream.db.StreamDatabase
import com.example.twitchstream.db.entity.TopGame
import com.example.twitchstream.db.entity.TopVideo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

private const val TAG = "Repository"

class Repository(
    private val context: Context,
    private val authApi: ApiRequests
    ) {
    private val db = StreamDatabase
    private var disposable: Disposable? = null

    /** Created by ID
     * date: 16-Jun-21, 1:20 PM
     * TODO: change to observable
     */
    fun getListFromLocal(): List<TopGame> {
        return db.invoke(context).streamDao().getAll()
    }

    fun saveToLocal(topGame: TopGame) {

        val tGame = topGame
        CoroutineScope(Dispatchers.IO).launch {
            val income = URL(topGame.game.logo?.small).openStream()

            income.use {
                tGame.game.logo?.small = Base64.encodeToString(it.readBytes(), Base64.DEFAULT)
                db.invoke(context).streamDao().insertAll(tGame)
            }
        }
    }

    fun getGamesFromRemote(offset: Int): Single<TopGameResponse> = authApi.topGames(offset)

    fun getVideosFromRemote(game: String): Single<TopVideosResponse> = authApi.topVideos(game)

    fun getVideo(videoId: String): Single<TopVideo> = authApi.getVideo(videoId)
}