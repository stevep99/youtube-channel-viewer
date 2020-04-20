package com.github.stevep.youtube.fragments

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.github.stevep.youtube.R
import com.github.stevep.youtube.data.Item
import com.github.stevep.youtube.network.GlideApp
import io.reactivex.subjects.PublishSubject

class VideoListAdapter
constructor(context: Context, private val data: List<Item>) : RecyclerView.Adapter<VideoListAdapter.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)
    private val clickedVideoSubject = PublishSubject.create<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_video, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.videoItemLayout.tag = position
        val thumbnailImage = data[position].snippet.thumbnails.get("high")
        if (thumbnailImage != null) {
            GlideApp.with(holder.itemView.context)
                    .load(Uri.parse(thumbnailImage.url))
                    .into(holder.videoImageView)
        }
        holder.titleView.text = data[position].snippet.title
    }

    override fun getItemCount(): Int {
        return data.size
    }


    fun getClickedVideoObserver() : PublishSubject<Int> {
        return clickedVideoSubject
    }

    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var videoItemLayout = itemView as ViewGroup
        internal var titleView: TextView = itemView.findViewById(R.id.videoTitle)
        internal var videoImageView: ImageView = itemView.findViewById(R.id.videoImageView)

        init {
            videoItemLayout.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            clickedVideoSubject.onNext(view.tag as Int)
        }
    }

}
