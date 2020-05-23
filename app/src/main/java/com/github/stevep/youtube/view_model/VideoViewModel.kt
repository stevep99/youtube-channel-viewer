package com.github.stevep.youtube.view_model

import androidx.lifecycle.ViewModel
import com.github.stevep.youtube.R
import com.github.stevep.youtube.VideoApp
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.github.stevep.youtube.data.*
import com.github.stevep.youtube.network.RequestApi


class VideoViewModel : ViewModel() {

    private var items: List<Item>? = null

    private val YOUTUBE_KEY = VideoApp.app.resources.getString(R.string.youtube_key)
    private val VIDEO_CHANNEL_ID = "UCoxcjq-8xIDTYp3uz647V5A"

    private val requestApi = RequestApi.getService()

    fun requestVideoItems() : Single<List<Item>> {
        return if (items == null) {
            requestApi.getChannelVideos(YOUTUBE_KEY, VIDEO_CHANNEL_ID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map { it.items }
                    .doOnSuccess {
                        this.items = it
                    }
        } else {
            Single.just(items)
        }
    }

    fun getVideoItems(): List<Item>? {
        return items
    }

}
