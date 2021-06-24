package com.example.twitchstream.di

import android.content.Context
import com.example.twitchstream.api.ApiRequests
import com.example.twitchstream.data.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @[Provides Singleton]
    fun repository(apiRequests: ApiRequests): Repository =
        Repository(context, apiRequests)
}