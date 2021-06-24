package com.example.twitchstream.di

import com.example.twitchstream.api.ApiRequests
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RemoteDataModule {
    @Provides @Singleton
    fun provideRetrofit(): Retrofit =
         Retrofit.Builder()
        .baseUrl("https://api.twitch.tv/kraken/")
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides @Singleton
    fun provideAuthApi(retrofit: Retrofit): ApiRequests =
        retrofit.create(ApiRequests::class.java)
}