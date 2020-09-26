package com.github.stevep.youtube.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.github.stevep.youtube.VideoApp.Companion.VIDEO_CHANNEL_ID
import com.github.stevep.youtube.VideoApp.Companion.YOUTUBE_KEY
import com.github.stevep.youtube.data.*
import com.github.stevep.youtube.network.RequestApi
import kotlinx.coroutines.Dispatchers


class VideoViewModel : ViewModel() {

    private var liveData : LiveData<YouTubeResponse>? = null

    private val requestApi = RequestApi.getService()

    fun requestVideoItems(): LiveData<YouTubeResponse> {
        liveData = liveData(Dispatchers.IO) {
            emit(requestApi.getChannelVideosAlt(YOUTUBE_KEY, VIDEO_CHANNEL_ID))
        }
        return liveData!!
    }

    fun getVideoItems(): List<Item>? {
        return liveData?.value?.items
    }

}
