package com.example.twitchstream.data

import android.content.Context
import android.util.Base64
import android.util.Log
import com.example.twitchstream.api.ApiClient
import com.example.twitchstream.api.response.TopGameResponse
import com.example.twitchstream.api.response.TopVideosResponse
import com.example.twitchstream.db.StreamDatabase
import com.example.twitchstream.db.entity.TopGame
import com.example.twitchstream.db.entity.TopVideo
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL

private const val TAG = "Repository"

class Repository(private val context: Context) {
    private val db = StreamDatabase
    private val api = ApiClient().api
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

    fun getGamesFromRemote(offset: Int): Single<TopGameResponse> = api.topGames(offset)

    fun getVideosFromRemote(game: String): Single<TopVideosResponse> = api.topVideos(game)
}