package com.example.twitchstream.view.video_full

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.twitchstream.R
import com.example.twitchstream.di.App
import com.example.twitchstream.util.GAME_ID
import com.example.twitchstream.viewmodel.FullVideoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "FullVideoFragment"
class FullVideoFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(FullVideoViewModel::class.java)
    }

    private var webView: WebView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_video_full, container, false)
        rootView.let {
            webView = it.findViewById(R.id.webView)
        }
        return rootView
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView?.settings?.javaScriptEnabled = true
        webView?.webChromeClient = WebChromeClient()
        CoroutineScope(Dispatchers.Main).launch {
            viewModel.video.collect {
                it?.url?.let { uri ->
                    Log.i(TAG, "--> onViewCreated: $uri")
                    webView?.loadUrl(uri)
                }
            }
        }


    }

    override fun onStart() {
        super.onStart()
        arguments?.let {
            viewModel.getVideo(it.getString(GAME_ID, ""))
        }
    }
}