package com.example.twitchstream.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.twitchstream.data.Repository
import com.example.twitchstream.db.entity.TopVideo
import com.example.twitchstream.di.App
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FullVideoViewModel"

class FullVideoViewModel : ViewModel() {
    @Inject
    lateinit var repository: Repository
    private var disposable: Disposable? = null

    private var _video: MutableStateFlow<TopVideo?> = MutableStateFlow(null)
    val video: StateFlow<TopVideo?> = _video

    init {
        App.getInstance().appComponent.inject(this)
    }

    fun getVideo(videoId: String): TopVideo? {
        var mVideo: TopVideo? = null
        disposable = repository.getVideo(videoId)
            .subscribeOn(Schedulers.io())
            ?.subscribe({
                it?.let {
                    mVideo = it
                    CoroutineScope(Dispatchers.IO).launch {
                        _video.emit(it)
                    }
                }
            }, {
                Log.e(TAG, "--> getVideo: ${it.message}")
            })
        return mVideo
    }
}