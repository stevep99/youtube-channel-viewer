package com.github.stevep.youtube

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.github.stevep.youtube.view_model.VideoViewModel

class VideoActivity : AppCompatActivity() {

    private lateinit var viewModel: VideoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_video)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(VideoViewModel::class.java)
    }

    override fun onSupportNavigateUp(): Boolean {
        if (!Navigation.findNavController(this, R.id.main_content).navigateUp()) {
            finish()
        }
        return true
    }

}