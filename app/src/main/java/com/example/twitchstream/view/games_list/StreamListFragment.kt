package com.example.twitchstream.view.games_list

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchstream.R
import com.example.twitchstream.data.NetworkChecker
import com.example.twitchstream.db.entity.TopGame
import com.example.twitchstream.util.GAME_NAME
import com.example.twitchstream.view.RateDialogFragment
import com.example.twitchstream.view.videos_list.VideosListFragment
import com.example.twitchstream.viewmodel.StreamListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val TAG = "StreamListFragment"

class StreamListFragment : NetworkChecker() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(StreamListViewModel::class.java)
    }
    private lateinit var adapter: StreamListAdapter
    private lateinit var manager: LinearLayoutManager
    private lateinit var rv: RecyclerView

    private var fragment: VideosListFragment? = null
    private var list: List<TopGame> = emptyList()
    private var isScrolling = false
    private var currentPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        if (fragment == null) fragment = VideosListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_stream_list, container, false)

        initViews(mView)
        return mView
    }

    private fun initViews(view: View) {
        view.let {
            rv = it.findViewById(R.id.stream_list_rv)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.topGames.observe(viewLifecycleOwner, {
            it?.let { it ->
                list = it
                        Log.i(TAG, "--> onViewCreated: status=${viewModel.status.value}")
                        adapter.updateLocal(viewModel.status.value)
                }
                adapter.updateList(list)
        })

        manager = LinearLayoutManager(requireContext())
        adapter = StreamListAdapter(list)
        rv.layoutManager = manager
        rv.adapter = adapter

        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    isScrolling = true
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val currentItem = manager.childCount
                val totalItems = manager.itemCount
                val outItems = manager.findFirstVisibleItemPosition()
                if (isScrolling && (currentItem + outItems == totalItems)) {
                    isScrolling = false
                    currentPage += 10
                    viewModel.getTopGames(currentPage)
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).launch {
            adapter?.game.collect { game ->
                Log.i(TAG, "--> onStart: game=${game}")
                if (game.isNotEmpty()){
                    val args = Bundle().apply {
                        putString(GAME_NAME, game)
                    }
                    fragment?.arguments = args

                    fragment?.let { fr ->
                        parentFragmentManager.beginTransaction()
                            .replace(R.id.container, fr)
                            .commit()
                    }
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.sendFeedback -> {
                Log.i(TAG, "--> onOptionsItemSelected: ")
                Toast.makeText(requireContext(), "Send feedback", Toast.LENGTH_SHORT)
                    .show()
                RateDialogFragment().show(childFragmentManager, "RateDialogFragment")
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}