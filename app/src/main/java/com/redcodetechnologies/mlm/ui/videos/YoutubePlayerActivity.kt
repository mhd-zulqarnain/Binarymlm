package com.redcodetechnologies.mlm.ui.videos


import android.os.Bundle
import android.util.Log

import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.utils.Constants


/**
 * Created by sonu on 10/11/17.
 *
 *
 * ### Here you need to extend the activity with YouTubeBaseActivity otherwise it will crash the app  ###
 */

class YoutubePlayerActivity : YouTubeBaseActivity() {
    private var videoID: String? = null
    private var youTubePlayerView: YouTubePlayerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_player)
        //get the video id
        videoID = intent.getStringExtra("video_id")
        youTubePlayerView = findViewById(R.id.youtube_player_view)
        initializeYoutubePlayer()
    }


    private fun initializeYoutubePlayer() {
        youTubePlayerView!!.initialize(Constants.DEVELOPER_KEY, object : YouTubePlayer.OnInitializedListener {

            override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer,
                                                 wasRestored: Boolean) {

                if (!wasRestored) {
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)

                    youTubePlayer.loadVideo(videoID)

                }
            }

            override fun onInitializationFailure(arg0: YouTubePlayer.Provider, arg1: YouTubeInitializationResult) {
                Log.e(TAG, "Youtube Player View initialization failed")
            }
        })
    }

    companion object {
        private val TAG = YoutubePlayerActivity::class.java.simpleName
    }

}
