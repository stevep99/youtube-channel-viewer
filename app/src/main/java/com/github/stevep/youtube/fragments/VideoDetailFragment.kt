package com.github.stevep.youtube.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.github.stevep.youtube.R
import com.github.stevep.youtube.data.Item
import com.github.stevep.youtube.network.GlideApp
import com.github.stevep.youtube.view_model.VideoViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_video_details.*


class VideoDetailFragment : Fragment() {

    private lateinit var viewModel: VideoViewModel

    private val subscribers = CompositeDisposable()

    companion object {
        fun newInstance(itemId: Int): VideoDetailFragment {
            val fragment = VideoDetailFragment()
            val args = Bundle()
            args.putInt("itemId", itemId)
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(VideoViewModel::class.java)

        val itemId = arguments?.getInt("itemId") ?: throw IllegalArgumentException("missing itemId")

        val item = viewModel.getVideoItems()?.get(itemId)
        if (item != null) {
            showItemDetails(item)
        }
    }

    private fun showItemDetails(item: Item) {
        val thumbnailImage = item.snippet.thumbnails.get("high")
        if (thumbnailImage != null) {
            GlideApp.with(this)
                    .load(Uri.parse(thumbnailImage.url))
                    .into(videoImageView)
        }
        videoTitle.text = item.snippet.title
        videoDetails.text = item.snippet.description
        videoPublishDate.text = item.snippet.publishedAt
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_video_details, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        subscribers.dispose()
    }
}