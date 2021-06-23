package com.example.twitchstream.di

import android.app.Application
import android.util.Log
import dagger.internal.InstanceFactory.create

private const val TAG = "App"

class App : Application() {

    lateinit var appComponent: AppComponent

    companion object {
        private var instance: App? = null

        fun getInstance(): App {
            return instance ?: App()
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(applicationContext))
            .remoteDataModule(RemoteDataModule())
            .build()
        Log.i(TAG, "--> onCreate: ")

    }
}