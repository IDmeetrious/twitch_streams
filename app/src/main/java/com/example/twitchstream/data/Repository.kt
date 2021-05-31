package com.example.twitchstream.data

import android.content.Context
import android.util.Base64
import android.util.Log
import com.example.twitchstream.api.ApiClient
import com.example.twitchstream.api.response.TopGameResponse
import com.example.twitchstream.db.StreamDatabase
import com.example.twitchstream.db.entity.TopGame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Url
import java.io.InputStream
import java.net.URL
import java.nio.charset.Charset

private const val TAG = "Repository"
class Repository(private val context: Context) {
    private val db = StreamDatabase
    private val api = ApiClient().api

    suspend fun getListFromLocal(): List<TopGame> {
        return db.invoke(context).streamDao().getAll()
    }

    fun saveToLocal(topGame: TopGame){
        val tGame = topGame
        CoroutineScope(Dispatchers.IO).launch {
            val income = URL(topGame.game.logo?.medium).openStream()
            income.use {
                tGame.game.logo?.small = Base64.encodeToString(it.readBytes(), Base64.URL_SAFE)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            db.invoke(context).streamDao().insertAll(tGame)
        }
    }

    fun getListFromRemote(): List<TopGame>{
        var list = emptyList<TopGame>()
        api.topGames().enqueue(object : Callback<TopGameResponse>{
            override fun onResponse(
                call: Call<TopGameResponse>,
                response: Response<TopGameResponse>
            ) {
                if (response.isSuccessful && response.body() != null){
                    response.body()!!.top?.let {
                        Log.i(TAG, "--> onResponse: ${it.size}")
                        list = it
                    }

                }
            }

            override fun onFailure(call: Call<TopGameResponse>, t: Throwable) {
                Log.e(TAG, "--> onFailure: ${t.message}")
            }

        })
        return list
    }
}