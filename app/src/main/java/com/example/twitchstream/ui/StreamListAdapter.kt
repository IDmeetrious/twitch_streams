package com.example.twitchstream.ui

import android.graphics.drawable.Drawable
import android.util.Base64
import android.util.Base64.decode
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.twitchstream.R
import com.example.twitchstream.db.entity.TopGame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "StreamListAdapter"

class StreamListAdapter(private var list: List<TopGame>) :
    RecyclerView.Adapter<StreamListAdapter.StreamViewHolder>() {
    class StreamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView
        val titleTv: TextView
        val channelsTv: TextView
        val viewersTv: TextView

        init {
            view.let {
                imageView = it.findViewById(R.id.stream_list_item_iv)
                titleTv = it.findViewById(R.id.stream_list_item_title_tv)
                channelsTv = it.findViewById(R.id.stream_list_item_channels_tv)
                viewersTv = it.findViewById(R.id.stream_list_item_views_tv)
            }
        }

    }

    fun updateList(list: List<TopGame>) {
        this.list += list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.stream_list_item, parent, false)
        return StreamViewHolder(view)
    }

    override fun onBindViewHolder(holder: StreamViewHolder, position: Int) {
        val item = list[position]


        item.let {

            Glide.with(holder.itemView)
                .load(it.game.logo?.large)
                .placeholder(android.R.drawable.gallery_thumb)
                .into(holder.imageView)

            holder.titleTv.text = it.game.name
            holder.channelsTv.text = "${it.channels}"
            holder.viewersTv.text = "${it.viewers}"
        }

    }

    override fun getItemCount(): Int = list.size
}