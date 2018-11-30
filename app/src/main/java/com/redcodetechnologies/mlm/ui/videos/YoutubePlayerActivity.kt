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

    /**
     * initialize the youtube player
     */
    private fun initializeYoutubePlayer() {
        youTubePlayerView!!.initialize(Constants.DEVELOPER_KEY, object : YouTubePlayer.OnInitializedListener {

            override fun onInitializationSuccess(provider: YouTubePlayer.Provider, youTubePlayer: YouTubePlayer,
                                                 wasRestored: Boolean) {

                //if initialization success then load the video id to youtube player
                if (!wasRestored) {
                    //set the player style here: like CHROMELESS, MINIMAL, DEFAULT
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)

                    //load the video
                    youTubePlayer.loadVideo(videoID)

                    //OR

                    //cue the video
                    //youTubePlayer.cueVideo(videoID);

                    //if you want when activity start it should be in full screen uncomment below comment
                    //  youTubePlayer.setFullscreen(true);

                    //If you want the video should play automatically then uncomment below comment
                    //  youTubePlayer.play();

                    //If you want to control the full screen event you can uncomment the below code
                    //Tell the player you want to control the fullscreen change
                    /*player.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
                    //Tell the player how to control the change
                    player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                        @Override
                        public void onFullscreen(boolean arg0) {
                            // do full screen stuff here, or don't.
                            Log.e(TAG,"Full screen mode");
                        }
                    });*/

                }
            }

            override fun onInitializationFailure(arg0: YouTubePlayer.Provider, arg1: YouTubeInitializationResult) {
                //print or show error if initialization failed
                Log.e(TAG, "Youtube Player View initialization failed")
            }
        })
    }

    companion object {
        private val TAG = YoutubePlayerActivity::class.java.simpleName
    }

}
