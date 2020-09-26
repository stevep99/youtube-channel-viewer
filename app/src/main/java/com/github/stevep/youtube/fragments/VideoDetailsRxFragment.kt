package com.github.stevep.youtube.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.github.stevep.youtube.R
import com.github.stevep.youtube.data.Item
import com.github.stevep.youtube.databinding.FragmentVideoDetailsBinding
import com.github.stevep.youtube.network.GlideApp
import com.github.stevep.youtube.view_model.VideoRxViewModel
import com.github.stevep.youtube.view_model.VideoViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_video_details.*


class VideoDetailRxFragment : Fragment() {

    private lateinit var viewModel: VideoRxViewModel
    private lateinit var binding: FragmentVideoDetailsBinding

    private val subscribers = CompositeDisposable()

    private val args: VideoDetailFragmentArgs by navArgs()


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(VideoRxViewModel::class.java)

        val item = viewModel.getVideoItems()?.get(args.videoId)

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
        binding.videoItem = item
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_video_details, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        subscribers.dispose()
    }
}