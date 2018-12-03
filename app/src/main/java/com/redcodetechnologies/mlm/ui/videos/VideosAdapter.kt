package com.redcodetechnologies.mlm.ui.videos

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.TextView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.VideosModal
import com.redcodetechnologies.mlm.models.users.Users
import com.redcodetechnologies.mlm.ui.network.adapter.DownMemberAdapter
import com.redcodetechnologies.mlm.utils.Constants

class VideosAdapter(var ctx: Context, var list: ArrayList<VideosModal>, private val onClick:(VideosModal)->Unit) : RecyclerView.Adapter<VideosAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_videos_list, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, p1: Int) {
        holder.bindView(list[p1])
        holder.itemView.setOnClickListener {
            onClick(list[p1])
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var youtubeVideoModelArrayList :  ArrayList<VideosModal>? = null
        var videoThumbnailImageView : YouTubeThumbnailView? = null
        var videoTitle : TextView? = null

        fun bindView(video: VideosModal) {
            videoThumbnailImageView = itemView.findViewById(R.id.video_thumbnail_image_view)
            videoTitle = itemView.findViewById(R.id.video_title_label)
            if(video.TrainingVideoName!=null)
                videoTitle!!.text = video.TrainingVideoName
                videoThumbnailImageView!!.initialize(Constants.DEVELOPER_KEY, object : YouTubeThumbnailView.OnInitializedListener {
                    override fun onInitializationSuccess(youTubeThumbnailView: YouTubeThumbnailView, youTubeThumbnailLoader: YouTubeThumbnailLoader) {
                        //when initialization is sucess, set the video id to thumbnail to load
                        youTubeThumbnailLoader.setVideo(video.TrainingVideoURL)

                        youTubeThumbnailLoader.setOnThumbnailLoadedListener(object : YouTubeThumbnailLoader.OnThumbnailLoadedListener {
                            override fun onThumbnailLoaded(youTubeThumbnailView: YouTubeThumbnailView, s: String) {
                                //when thumbnail loaded successfully release the thumbnail loader as we are showing thumbnail in adapter
                                youTubeThumbnailLoader.release()
                            }

                            override fun onThumbnailError(youTubeThumbnailView: YouTubeThumbnailView, errorReason: YouTubeThumbnailLoader.ErrorReason) {
                                //print or show error when thumbnail load failed
                                Log.e(TAG, "Youtube Thumbnail Error")
                            }
                        })
                    }

                    override fun onInitializationFailure(youTubeThumbnailView: YouTubeThumbnailView, youTubeInitializationResult: YouTubeInitializationResult) {
                        //print or show error when initialization failed
                        Log.e(TAG, "Youtube Initialization Failure")

                    }
                })
        }

    }

}