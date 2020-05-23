package com.github.stevep.youtube.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import com.github.stevep.youtube.view_model.VideoViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import com.github.stevep.youtube.R
import com.github.stevep.youtube.data.Item
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.github.stevep.youtube.VideoActivity
import kotlinx.android.synthetic.main.fragment_video_list.*


class VideoListFragment : Fragment() {

    private lateinit var viewModel: VideoViewModel

    private val subscribers = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_video_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(VideoViewModel::class.java)

        loadingProgress.visibility = View.VISIBLE
        subscribers.add(viewModel.requestVideoItems()
                .doFinally { loadingProgress.visibility = View.GONE }
                .subscribe(
                        { populateRecyclerView(it) },
                        { Toast.makeText(context, "Failed to load videos", Toast.LENGTH_SHORT).show() }
                ))
    }

    fun populateRecyclerView(items: List<Item>) {
        val ctx = context ?: return

        val recyclerView = view?.findViewById<RecyclerView>(R.id.videosRecyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(ctx)

        val videoAdapter = VideoListAdapter(ctx, items)
        recyclerView?.adapter = videoAdapter

        subscribers.add(videoAdapter.getClickedVideoObserver()
                .subscribe { itemId -> (activity as VideoActivity).showDetailFragment(itemId) }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        subscribers.dispose()
    }

}

