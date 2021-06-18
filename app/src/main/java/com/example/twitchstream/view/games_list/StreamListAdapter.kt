package com.example.twitchstream.view.games_list

import android.graphics.BitmapFactory
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
import com.example.twitchstream.R
import com.example.twitchstream.db.entity.TopGame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val TAG = "StreamListAdapter"

class StreamListAdapter(private var list: List<TopGame>) :
    RecyclerView.Adapter<StreamListAdapter.StreamViewHolder>() {
    private var isPersistent: Boolean = false
    private var _game: MutableStateFlow<String> = MutableStateFlow("")
    val game: StateFlow<String> = _game

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

    fun updateLocal(status: Boolean){
        Log.i(TAG, "--> updateLocal: ")
        isPersistent = status
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StreamViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.stream_list_item, parent, false)
        return StreamViewHolder(view)
    }

    override fun onBindViewHolder(holder: StreamViewHolder, position: Int) {
        val item = list[position]


        item.let {
            Log.i(TAG, "--> onBindViewHolder: persistentStatus[$isPersistent]")
            if (isPersistent) {
                Log.i(TAG, "--> onBindViewHolder: length[${it.game.logo?.small?.length}]")
                CoroutineScope(Dispatchers.Main).launch {
                    val byteImg = decode(it.game.logo?.small, Base64.DEFAULT)
                    Log.i(TAG, "--> onBindViewHolder: imageString[${it.game.logo?.small}]")
                    Log.i(TAG, "--> onBindViewHolder: size[${byteImg.size}]")
                    val decodedImg = BitmapFactory.decodeByteArray(byteImg, 0, byteImg.size)
                    holder.imageView.setImageBitmap(decodedImg)
                }
            } else {
                Glide.with(holder.itemView)
                    .load(it.game.logo?.small)
                    .placeholder(android.R.drawable.gallery_thumb)
                    .into(holder.imageView)
            }

            holder.titleTv.text = it.game.name
            holder.channelsTv.text = "${it.channels}"
            holder.viewersTv.text = "${it.viewers}"
        }
        holder.itemView.setOnClickListener {
            Log.i(TAG, "--> onBindViewClicked: ${item.game.name}")
            CoroutineScope(Dispatchers.IO).launch {
                _game.emit(item.game.name)
            }
//            _game.value = item.game.name
        }

    }

    override fun getItemCount(): Int = list.size
}