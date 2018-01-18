package com.tolodev.youtubeplayer.application

import android.app.Application
import com.tolodev.youtubeplayer.manager.YoutubeControlManager

class YouTubePlayerApplication : Application() {

    companion object {
        @JvmStatic
        lateinit var app: YouTubePlayerApplication
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        YoutubeControlManager.initYoutubeControlManager()
    }

}