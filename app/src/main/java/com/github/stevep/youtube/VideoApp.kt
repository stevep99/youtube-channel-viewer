package com.github.stevep.youtube

import android.app.Application

class VideoApp : Application() {

    companion object {
        lateinit var app: VideoApp
    }

    init {
        app = this
    }

}

