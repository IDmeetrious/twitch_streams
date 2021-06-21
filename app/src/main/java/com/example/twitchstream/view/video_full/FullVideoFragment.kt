package com.example.twitchstream.view.video_full

import android.annotation.SuppressLint
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

//    private var videoView: VideoView? = null
    private var webView: WebView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_video_full, container, false)
        rootView.let {
//            videoView = it.findViewById(R.id.videoView)
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
                    /** Created by ID
                     * date: 18-Jun-21, 3:44 PM
                     * TODO: doesn't work with this url format "www.example.com/video12312312"
                     */
//                    videoView?.setVideoURI(Uri.parse(uri))
//                    videoView?.setMediaController(MediaController(requireContext()))
//                    videoView?.requestFocus(0)
//                    videoView?.start()
//                    webView?.loadUrl("file:///android_asset/web/index.html")
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