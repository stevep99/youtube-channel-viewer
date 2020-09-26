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
import retrofit2.http.Headers


class RequestApi {

    companion object {
        private val REQUEST_API = "https://www.googleapis.com/"
        private var service: RequestService? = null

        fun getService(): RequestService {
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
        @Headers(
            "X-Android-Package: com.github.stevep.youtube",
            "X-Android-Cert: FD:8A:80:98:9F:04:B3:24:72:AB:F1:BE:87:16:1C:E5:F0:1D:7B:8A"
        )
        @GET("/youtube/v3/search?part=snippet&order=date")
        fun getChannelVideos(@Query(value="key") key: String, @Query("channelId") ChannelId : String): Single<YouTubeResponse>

        @GET("/youtube/v3/search?part=snippet&order=date")
        suspend fun getChannelVideosAlt(@Query(value="key") key: String, @Query("channelId") ChannelId : String): YouTubeResponse
    }

}