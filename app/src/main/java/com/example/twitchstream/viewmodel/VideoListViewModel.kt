package com.example.twitchstream.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.twitchstream.data.Repository
import com.example.twitchstream.db.entity.TopVideo
import com.example.twitchstream.di.App
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

private const val TAG = "VideoListViewModel"

class VideoListViewModel : ViewModel() {
    @Inject
    lateinit var repository: Repository
    private var disposable: Disposable? = null
    private var _topVideos = MutableLiveData<List<TopVideo>>()
    val topVideos: LiveData<List<TopVideo>> = _topVideos

    init {
        App.getInstance().appComponent.inject(this)
    }

    fun getTopVideos(game: String = ""): List<TopVideo> {
        var list: List<TopVideo> = emptyList()
        disposable = repository.getVideosFromRemote(game)
            .subscribeOn(Schedulers.io())
            ?.subscribe({ response ->
                response?.vods?.let {
                    _topVideos.postValue(it)
                }
            }, {
                Log.e(TAG, "--> getTopVideos: ${it.message}")
            })
        return list
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}