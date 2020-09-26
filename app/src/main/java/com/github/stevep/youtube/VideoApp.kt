package com.github.stevep.youtube

import android.app.Application

class VideoApp : Application() {

    companion object {
        lateinit var app: VideoApp
        lateinit var YOUTUBE_KEY: String
        val VIDEO_CHANNEL_ID = "UCoxcjq-8xIDTYp3uz647V5A"
    }

    override fun onCreate() {
        super.onCreate()

        app = this
        YOUTUBE_KEY = resources.getString(R.string.youtube_key)
    }

}

