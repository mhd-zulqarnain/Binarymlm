package com.redcodetechnologies.mlm.ui.videos

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.google.android.youtube.player.YouTubeThumbnailView
import com.redcodetechnologies.mlm.R


class YoutubeViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    var videoThumbnailImageView: YouTubeThumbnailView
    var videoTitle:TextView
    var videoDuration:TextView

    init{
        videoThumbnailImageView = itemView.findViewById(R.id.video_thumbnail_image_view)
        videoTitle = itemView.findViewById(R.id.video_title_label)
        videoDuration = itemView.findViewById(R.id.video_duration_label)
    }
}