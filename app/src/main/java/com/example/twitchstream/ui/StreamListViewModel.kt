package com.example.twitchstream.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.twitchstream.api.ApiClient
import com.example.twitchstream.api.response.TopGameResponse
import com.example.twitchstream.app.App
import com.example.twitchstream.data.Repository
import com.example.twitchstream.db.entity.TopGame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "StreamListViewModel"
class StreamListViewModel(): ViewModel() {
    private val repository = Repository(App.getInstance().applicationContext)

    private var _topGames = MutableLiveData<List<TopGame>>()
    val topGames: LiveData<List<TopGame>> = _topGames

    init {
        getTopGames()
    }

    fun getTopGames(offset: Int = 0){
        ApiClient().api.topGames(offset).enqueue(object : Callback<TopGameResponse>{

            override fun onResponse(
                call: Call<TopGameResponse>,
                response: Response<TopGameResponse>
            ) {
                if (response.isSuccessful && response.code() == 200){
                    Log.i(TAG, "--> onResponse: ${response.body()}")
                    response.body()?.top.let {
                        // Update liveData
                        _topGames.postValue(it)
                        // Insert to persistent
                        it?.forEach { topGame ->
                            repository.saveToLocal(topGame)
                        }
                    }

                }
            }

            override fun onFailure(call: Call<TopGameResponse>, t: Throwable) {
                Log.e(TAG, "--> onFailure: ${t.message}")
                // Try to load from local
                CoroutineScope(Dispatchers.IO).launch {
                    repository.getListFromLocal()?.let {
                        Log.i(TAG, "--> onFailure: loadFromLocal[${it.size}]")
                        _topGames.postValue(it)
                    }
                }

            }

        })
    }
}