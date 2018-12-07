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
import android.widget.Toast
import android.widget.VideoView
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

class  VideoDetailActivity : AppCompatActivity() {
    var adapter: VideoDetailAdapter? = null
    var list: ArrayList<PackVideo> = ArrayList()
    private var recyclerView: RecyclerView? = null
    var disposable: Disposable? = null
    var prefs = SharedPrefs.getInstance()!!
    var pckgId: Int? = null
    var catId: Int? = null
     val VEDIO_BASE_URL = "http://www.sleepingpartnermanagementportalrct.com/VideosPack/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vedio_detail)

        this.getSupportActionBar()?.setTitle("Training Videos")
        this.getSupportActionBar()?.setIcon(0)
        catId = intent.getStringExtra("categoryId").toInt()

        if (prefs.getUser(this).userId != null) {
            pckgId = prefs.getUser(this).userPackage
        }
        initView()
        /*fullscreenVideoView.videoUrl("http://www.sleepingpartnermanagementportalrct.com/VideosPack/awvptfay.h1c.mp4")
                .enableAutoStart()*/
        val videoPath = "https://clips.vorwaerts-gmbh.de/VfE_html5.mp4"
        val uri = Uri.parse(videoPath)
        fullscreenVideoView.setVideoURI(uri);
    }

    private fun initView() {
        recyclerView = findViewById(R.id.recylcer_videos_pkg)
        recyclerView!!.layoutManager = LinearLayoutManagerWrapper(this@VideoDetailActivity, LinearLayout.VERTICAL, false)
        adapter = VideoDetailAdapter(this@VideoDetailActivity, list) { obj ->
            fullscreenVideoView.reset()
            showVedioDialog(obj.VideoPackVideos!!)
        }

        recyclerView!!.adapter = adapter
        recyclerView!!.setItemAnimator(null);

        makeVideos()
    }

    private fun showVedioDialog(url:String) {

        val uri = Uri.parse("http://www.sleepingpartnermanagementportalrct.com/VideosPack/"+url)
        fullscreenVideoView.setVideoURI(uri);

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
        val observable: Observable<ArrayList<PackVideo>> = MyApiRxClint.getInstance()!!.getService()!!.getvideolist("bearer ",pckgId.toString()!!,catId!!)
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

}