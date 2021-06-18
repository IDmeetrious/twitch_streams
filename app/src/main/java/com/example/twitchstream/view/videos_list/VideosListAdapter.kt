package com.example.twitchstream.view.videos_list

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.twitchstream.R
import com.example.twitchstream.db.entity.TopVideo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "VideosListAdapter"

class VideosListAdapter(private var list: List<TopVideo>) :
    RecyclerView.Adapter<VideoViewHolder>() {

    private var _videoId: MutableStateFlow<String> = MutableStateFlow("")
    val videoId: StateFlow<String> = _videoId

    fun setList(list: List<TopVideo>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.videos_list_item, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val item = list[position]
        holder.title?.text = item.title
        holder.views?.text = "${item.views}"
        holder.publishDate?.text = item.publishedAt
        holder.image?.let { iv ->
            item.previewUrl?.let { it ->
                Log.i(TAG, "--> onBindVideoPreview: medium=${it.medium}")
                Glide.with(holder.itemView)
                    .load(it.medium)
                    .into(iv)
            }
        }
        holder.itemView.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                _videoId.emit(item.id)
            }
        }
    }

    override fun getItemCount(): Int = list.size
}