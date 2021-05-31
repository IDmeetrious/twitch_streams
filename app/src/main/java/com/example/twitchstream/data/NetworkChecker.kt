package com.example.twitchstream.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

private const val TAG = "NetworkChecker"

open class NetworkChecker : Fragment() {
    private lateinit var cm: ConnectivityManager
    private lateinit var repository: Repository

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
        repository = Repository(requireContext())
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