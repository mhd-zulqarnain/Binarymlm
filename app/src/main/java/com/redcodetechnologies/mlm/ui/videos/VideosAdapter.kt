package com.redcodetechnologies.mlm.ui.videos

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.VideosModal
import com.redcodetechnologies.mlm.utils.Constants

class VideosAdapter:RecyclerView.Adapter<YoutubeViewHolder> {
    private val TAG = VideosAdapter::class.java.getSimpleName()
    private var context: Context? = null
    private var youtubeVideoModelArrayList: ArrayList<VideosModal> = ArrayList()


    constructor(context: Context, youtubeVideoModelArrayList: ArrayList<VideosModal>) {
        this.context = context
        this.youtubeVideoModelArrayList = youtubeVideoModelArrayList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.getContext())
        val view = layoutInflater.inflate(R.layout.single_videos_list, parent, false)
        return YoutubeViewHolder(view)
    }

    override fun onBindViewHolder(holder: YoutubeViewHolder, position: Int) {
        val youtubeVideoModel = youtubeVideoModelArrayList.get(position)
        holder.videoTitle.setText(youtubeVideoModel.getTitle())
        holder.videoThumbnailImageView.initialize(Constants.DEVELOPER_KEY, object : YouTubeThumbnailView.OnInitializedListener {
            override fun onInitializationSuccess(youTubeThumbnailView: YouTubeThumbnailView, youTubeThumbnailLoader: YouTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(youtubeVideoModel.getVideoId())
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(object : YouTubeThumbnailLoader.OnThumbnailLoadedListener {
                    override fun onThumbnailLoaded(youTubeThumbnailView: YouTubeThumbnailView, s: String) {
                        youTubeThumbnailLoader.release()
                    }

                    override fun onThumbnailError(youTubeThumbnailView: YouTubeThumbnailView, errorReason: YouTubeThumbnailLoader.ErrorReason) {}
                })
            }

            override fun onInitializationFailure(youTubeThumbnailView: YouTubeThumbnailView, youTubeInitializationResult: YouTubeInitializationResult) {
            }
        })
    }

    override fun getItemCount(): Int {
         if (youtubeVideoModelArrayList != null)
             return youtubeVideoModelArrayList.size
        else return 0
    }
}