package com.example.twitchstream.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.twitchstream.app.App
import com.example.twitchstream.data.Repository
import com.example.twitchstream.db.entity.TopGame
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private const val TAG = "StreamListViewModel"
class StreamListViewModel(): ViewModel() {
    private val repository = Repository(App.getInstance().applicationContext)
    private var disposable: Disposable? = null

    private var _status = MutableStateFlow(false)
    val status: StateFlow<Boolean> = _status

    private var _topGames = MutableLiveData<List<TopGame>>()
    val topGames: LiveData<List<TopGame>> = _topGames

    init {
        getTopGames()
    }

    fun getTopGames(offset: Int = 0){
        disposable = repository.getGamesFromRemote(offset)
            .subscribeOn(Schedulers.io())
            .subscribe({ response ->
                response?.top?.let { list ->
                    _status.value = false
                    _topGames.postValue(list)
                    list.forEach { game ->
                        repository.saveToLocal(game)
                    }
                }
            }, {
                _status.value = true
                repository.getListFromLocal()?.let {
                    Log.i(TAG, "--> onFailure: loadFromLocal[${it.size}]")
                    _topGames.postValue(it)
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}