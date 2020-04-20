package com.github.stevep.youtube.network

import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import com.github.stevep.youtube.data.YouTubeResponse
import okhttp3.logging.HttpLoggingInterceptor


class RequestApi {

    companion object {
        private val REQUEST_API = "https://www.googleapis.com/youtube/v3/"

        private const val YOUTUBE_API_KEY ="AIzaSyD6rR4tJQQDSpyqMm0nsnNg9G1IgeY_xsU"
        private const val VIDEOS_PATH = "search?key=" + YOUTUBE_API_KEY + "&part=snippet&order=date"

        private var service: RequestService? = null

        fun getInstance() : RequestService {
            if (service == null) {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)

                var client = OkHttpClient.Builder()
                        .addInterceptor(logging)
                        .build()

                var retrofit: Retrofit = Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .baseUrl(REQUEST_API)
                        .client(client)
                        .build()

                service = retrofit.create(RequestService::class.java)
            }
            return service!!
        }
    }

    interface RequestService {
        @GET(VIDEOS_PATH)
        fun getChannelVideos(@Query("channelId") ChannelId : String): Single<YouTubeResponse>
    }

}