package com.example.twitchstream.di

import com.example.twitchstream.data.Repository
import com.example.twitchstream.viewmodel.FullVideoViewModel
import com.example.twitchstream.viewmodel.StreamListViewModel
import com.example.twitchstream.viewmodel.VideoListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        RemoteDataModule::class
    ]
)
interface AppComponent {
    fun inject(fullVideoViewModel: FullVideoViewModel)
    fun inject(streamListViewModel: StreamListViewModel)
    fun inject(videoListViewModel: VideoListViewModel)
}