package com.example.twitchstream.app

import android.app.Application
import android.content.Context
import android.util.Log
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

private const val TAG = "App"
class App: Application() {

    companion object{
        private var instance: App? = null

        fun getInstance(): App {
            return instance ?: App()
        }
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
        Log.i(TAG, "--> onCreate: ")
    }
}