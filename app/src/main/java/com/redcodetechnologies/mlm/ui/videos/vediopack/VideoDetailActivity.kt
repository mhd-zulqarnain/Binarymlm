package com.redcodetechnologies.mlm.ui.videos.vediopack

import io.reactivex.Observable
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.VideoView
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.MappingTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.BandwidthMeter
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.util.Util
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.PackVideo
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.LinearLayoutManagerWrapper
import com.redcodetechnologies.mlm.utils.SharedPrefs
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_vedio_detail.*

import java.util.*

class VideoDetailActivity : AppCompatActivity() {
    var adapter: VideoDetailAdapter? = null
    var list: ArrayList<PackVideo> = ArrayList()
    private var recyclerView: RecyclerView? = null
    var disposable: Disposable? = null
    var prefs = SharedPrefs.getInstance()!!
    var pckgId: Int? = null
    var catId: Int? = null
    private var player: SimpleExoPlayer? = null

    val VEDIO_BASE_URL = "http://www.sleepingpartnermanagementportalrct.com/VideosPack/"


    //vedio player
    companion object {
        private const val KEY_PLAY_WHEN_READY = "play_when_ready"
        private const val KEY_WINDOW = "window"
        private const val KEY_POSITION = "position"
    }

    private val playerView: PlayerView by lazy { findViewById<PlayerView>(R.id.player_view) }


    private var shouldAutoPlay: Boolean = true
    private var trackSelector: DefaultTrackSelector? = null
    private var lastSeenTrackGroupArray: TrackGroupArray? = null
    private lateinit var mediaDataSourceFactory: DataSource.Factory
    private val bandwidthMeter: BandwidthMeter = DefaultBandwidthMeter()

    private var playWhenReady: Boolean = false
    private var currentWindow: Int = 0
    private var playbackPosition: Long = 0

    private val progressBar: ProgressBar by lazy { findViewById<ProgressBar>(R.id.progress_bar) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vedio_detail)

        this.getSupportActionBar()?.setTitle("Training Videos")
        this.getSupportActionBar()?.setIcon(0)
        catId = intent.getStringExtra("categoryId").toInt()

        if (prefs.getUser(this).userId != null) {
            pckgId = prefs.getUser(this).userPackage
        }

        if (savedInstanceState != null) {

            with(savedInstanceState) {
                playWhenReady = getBoolean(KEY_PLAY_WHEN_READY)
                currentWindow = getInt(KEY_WINDOW)
                playbackPosition = getLong(KEY_POSITION)
            }
        }

        initView()
        /*fullscreenVideoView.videoUrl("http://www.sleepingpartnermanagementportalrct.com/VideosPack/awvptfay.h1c.mp4")
                .enableAutoStart()*/
        val videoPath = "https://clips.vorwaerts-gmbh.de/VfE_html5.mp4"
        val uri = Uri.parse(videoPath)
        //fullscreenVideoView.setVideoURI(uri);
    }

    private fun initView() {
        recyclerView = findViewById(R.id.recylcer_videos_pkg)
        recyclerView!!.layoutManager = LinearLayoutManagerWrapper(this@VideoDetailActivity, LinearLayout.VERTICAL, false)
        adapter = VideoDetailAdapter(this@VideoDetailActivity, list) { obj ->
            //fullscreenVideoView.reset()
            showVedioDialog(obj.VideoPackVideos!!)
        }

        recyclerView!!.adapter = adapter
        recyclerView!!.setItemAnimator(null);

        makeVideos()
    }

    private fun showVedioDialog(url: String) {

        val uri = Uri.parse("http://www.sleepingpartnermanagementportalrct.com/VideosPack/" + url)
        //  fullscreenVideoView.setVideoURI(uri);

        /* fullscreenVideoView.videoUrl("http://www.sleepingpartnermanagementportalrct.com/VideosPack/"+url)
                 .enableAutoStart()*/

        /* val view: View = LayoutInflater.from(this).inflate(R.layout.dialog_vedio_view, null)
         val alertBox = AlertDialog.Builder(this)
         alertBox.setView(view)
         alertBox.setCancelable(true)
         val dialog = alertBox.create()
         val ve_vedio: FullscreenVideoView = view.findViewById(R.id.fullscreenVideoView)
         dialog.window.setBackgroundDrawableResource(android.R.color.transparent);
         val baseUrl = "http://www.sleepingpartnermanagementportalrct.com/VideosPack/"+url
         val uri = Uri.parse(baseUrl)

         ve_vedio.videoUrl(baseUrl)


         dialog.show()*/

    }

    fun makeVideos() {

        if (!Apputils.isNetworkAvailable(this)) {
            Toast.makeText(this, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        if (!list.isEmpty())
            list.clear()


        val observer = getObserver()
        val observable: Observable<ArrayList<PackVideo>> = MyApiRxClint.getInstance()!!.getService()!!.getvideolist("bearer ", pckgId.toString()!!, catId!!)
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)

    }

    fun getObserver(): Observer<ArrayList<PackVideo>> {

        return object : Observer<ArrayList<PackVideo>> {
            override fun onComplete() {


            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onNext(t: ArrayList<PackVideo>) {
                //if(t[0].VideoPackVideos!=null)
                // fullscreenVideoView.videoUrl("http://www.sleepingpartnermanagementportalrct.com/VideosPack/"+t[0].VideoPackVideos)

                t.forEach { obj ->
                    list.add(obj)

                }
                adapter!!.notifyDataSetChanged()

                if (t.size == 0) {
                    recyclerView!!.visibility = View.GONE
                } else {
                    recyclerView!!.visibility = View.VISIBLE
                }
            }

            override fun onError(e: Throwable) {
                print("error")
            }
        }
    }

    private fun initializePlayer() {

        playerView.requestFocus()

        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)

        trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        lastSeenTrackGroupArray = null

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)

        playerView.player = player

        with(player!!) {
            addListener(PlayerEventListener())
            playWhenReady = shouldAutoPlay
        }

        // Use Hls, Dash or other smooth streaming media source if you want to test the track quality selection.
        /*val mediaSource: MediaSource = HlsMediaSource(Uri.parse("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"),
                mediaDataSourceFactory, mainHandler, null)*/

        val mediaSource = ExtractorMediaSource.Factory(mediaDataSourceFactory)
                .createMediaSource(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))

        val haveStartPosition = currentWindow != C.INDEX_UNSET
        if (haveStartPosition) {
            player!!.seekTo(currentWindow, playbackPosition)
        }

        player!!.prepare(mediaSource, !haveStartPosition, false)
        updateButtonVisibilities()

        //  ivHideControllerButton.setOnClickListener { playerView.hideController() }
    }

    private fun releasePlayer() {
        if (player != null) {
            updateStartPosition()
            shouldAutoPlay = player!!.playWhenReady
            player!!.release()
            player = null
            trackSelector = null
        }
    }

    private fun updateStartPosition() {

        with(player!!) {
            playbackPosition = currentPosition
            currentWindow = currentWindowIndex
            playWhenReady = playWhenReady
        }
    }

    private fun updateButtonVisibilities() {
        // ivSettings.visibility = View.GONE
        if (player == null) {
            return
        }

        val mappedTrackInfo = trackSelector!!.currentMappedTrackInfo ?: return

        /*for (i in 0 until mappedTrackInfo.rendererCount) {
            val trackGroups = mappedTrackInfo.getTrackGroups(i)
            if (trackGroups.length != 0) {
                if (player!!.getRendererType(i) == C.TRACK_TYPE_VIDEO) {
                    *//*ivSettings.visibility = View.VISIBLE
                    ivSettings.setOnClickListener(this)
                    ivSettings.tag = i*//*
                }
            }
        }*/
    }

    public override fun onStart() {
        super.onStart()

        if (Util.SDK_INT > 23) initializePlayer()
    }

    public override fun onResume() {
        super.onResume()

        if (Util.SDK_INT <= 23 || player == null) initializePlayer()
    }

    public override fun onPause() {
        super.onPause()

        if (Util.SDK_INT <= 23) releasePlayer()
    }

    public override fun onStop() {
        super.onStop()

        if (Util.SDK_INT > 23) releasePlayer()
    }

    private inner class PlayerEventListener : Player.DefaultEventListener() {

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            when (playbackState) {
                Player.STATE_IDLE       // The player does not have any media to play yet.
                -> progressBar.visibility = View.VISIBLE
                Player.STATE_BUFFERING  // The player is buffering (loading the content)
                -> progressBar.visibility = View.VISIBLE
                Player.STATE_READY      // The player is able to immediately play
                -> progressBar.visibility = View.GONE
                Player.STATE_ENDED      // The player has finished playing the media
                -> progressBar.visibility = View.GONE
            }
            updateButtonVisibilities()
        }

        override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
            updateButtonVisibilities()
            // The video tracks are no supported in this device.
            if (trackGroups !== lastSeenTrackGroupArray) {
                val mappedTrackInfo = trackSelector!!.currentMappedTrackInfo

                lastSeenTrackGroupArray = trackGroups
            }
        }

    }

}