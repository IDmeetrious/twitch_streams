package com.example.twitchstream.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.twitchstream.R
import com.example.twitchstream.view.games_list.StreamListFragment
import com.example.twitchstream.view.videos_list.VideosListFragment
import com.example.twitchstream.view.widget.ProgressView
import com.example.twitchstream.viewmodel.VideoListViewModel

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val progressView = ProgressView(this, null)
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onBackPressed() {
        supportFragmentManager.let { fm ->
            val fr: Fragment? = fm.findFragmentById(R.id.container)
            Log.i(TAG, "--> onBackStackPressed: ")
            fr?.let {
                /** Created by ID
                 * date: 21-Jun-21, 2:58 PM
                 * TODO: add full video fragment
                 */
                if (it is VideosListFragment)
                    fm.beginTransaction()
                        .replace(R.id.container, StreamListFragment())
                        .commit()
                else
                    super.onBackPressed()
            }
        }

    }
}