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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.github.stevep.youtube.view_model.VideoRxViewModel
import kotlinx.android.synthetic.main.fragment_video_list.*


class VideoListRxFragment : Fragment() {

    private lateinit var viewRxModel: VideoRxViewModel

    private val subscribers = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_video_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        loadingProgress.visibility = View.VISIBLE

        viewRxModel = ViewModelProvider(requireActivity()).get(VideoRxViewModel::class.java)
        subscribers.add(viewRxModel.requestVideoItems()
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
                .subscribe { itemId ->
                    val action = VideoListRxFragmentDirections.actionVideoListRxFragmentToVideoDetailRxFragment(itemId)
                    findNavController(this).navigate(action)
                }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        subscribers.dispose()
    }

}

