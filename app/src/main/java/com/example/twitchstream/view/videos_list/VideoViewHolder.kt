package com.example.twitchstream.view.videos_list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.twitchstream.R

class VideoViewHolder(view: View): RecyclerView.ViewHolder(view) {
    var image: ImageView? = null
    var title: TextView? = null
    var publishDate: TextView? = null
    var views: TextView? = null

    init {
        view?.let {
            image = it.findViewById(R.id.videos_list_item_iv)
            title = it.findViewById(R.id.videos_list_item_title_tv)
            publishDate = it.findViewById(R.id.videos_list_item_publish_date_tv)
            views = it.findViewById(R.id.videos_list_item_views_tv)
        }
    }
}