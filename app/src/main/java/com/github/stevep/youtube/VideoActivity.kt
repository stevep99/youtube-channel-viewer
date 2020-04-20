package com.github.stevep.youtube

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.github.stevep.youtube.fragments.VideoDetailFragment
import com.github.stevep.youtube.fragments.VideoListFragment
import com.github.stevep.youtube.view_model.VideoViewModel

class VideoActivity : AppCompatActivity() {

    private lateinit var viewModel: VideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_video)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this).get(VideoViewModel::class.java)

        if (savedInstanceState == null) {
            showListFragment()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val goBack = supportFragmentManager.backStackEntryCount > 0
        if (goBack) {
            supportFragmentManager.popBackStack()
            viewModel.setDetailItemId(null)
        } else {
            finish()
        }

        return true
    }

    private fun showListFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, VideoListFragment())
        transaction.commit()
        viewModel.setDetailItemId(null)
    }

    fun showDetailFragment(itemId: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, VideoDetailFragment.newInstance(itemId))
        transaction.addToBackStack(null)
        transaction.commit()
        viewModel.setDetailItemId(itemId)
    }

}