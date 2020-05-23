package com.github.stevep.youtube.view_model

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.github.stevep.youtube.data.*
import com.github.stevep.youtube.network.RequestApi


class VideoViewModel : ViewModel() {

    private val VIDEO_CHANNEL_ID = "UC_A--fhX5gea0i4UtpD99Gg"

    private var items: List<Item>? = null
    private var detailItemId: Int? = null

    private val requestApi = RequestApi.getInstance()

    fun requestVideoItems() : Single<List<Item>> {
        return if (items == null) {
            requestApi.getChannelVideos(VIDEO_CHANNEL_ID)
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

    fun setDetailItemId(item: Int?) {
        this.detailItemId = item
    }

    fun getDetailItemId(): Int? {
        return detailItemId
    }

}
