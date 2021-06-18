package com.example.twitchstream.view.video_full

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.fragment.app.Fragment
import com.example.twitchstream.R
import com.example.twitchstream.db.entity.TopGame

class FullVideoFragment: Fragment() {
    private var videoView: VideoView? = null
    private var game: TopGame? = null
    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        arguments?.let {
            
        }

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_video_full, container, false)
        rootView.let {
            videoView = it.findViewById(R.id.videoView)
        }
        return rootView
    }
}