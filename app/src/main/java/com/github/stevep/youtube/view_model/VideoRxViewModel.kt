package com.github.stevep.youtube.view_model

import androidx.lifecycle.ViewModel
import com.github.stevep.youtube.VideoApp.Companion.VIDEO_CHANNEL_ID
import com.github.stevep.youtube.VideoApp.Companion.YOUTUBE_KEY
import com.github.stevep.youtube.data.Item
import com.github.stevep.youtube.network.RequestApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class VideoRxViewModel : ViewModel() {

    private var items: List<Item>? = null

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
