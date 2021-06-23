package com.example.twitchstream.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import javax.inject.Inject

private const val TAG = "NetworkChecker"

open class NetworkChecker : Fragment() {
    private lateinit var cm: ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.i(TAG, "--> onAvailable: ")
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            Log.i(TAG, "--> onLost: ")

        }

        override fun onUnavailable() {
            super.onUnavailable()
            Log.e(TAG, "--> onUnavailable: ")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onStart() {
        super.onStart()
        cm.registerNetworkCallback(NetworkRequest.Builder().build(), networkCallback)
    }

    override fun onStop() {
        super.onStop()
        cm.unregisterNetworkCallback(networkCallback)
    }
}