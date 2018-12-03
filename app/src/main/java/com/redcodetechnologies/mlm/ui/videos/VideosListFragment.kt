package com.redcodetechnologies.mlm.ui.videos


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.telecom.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import com.google.gson.Gson
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.VideosModal
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.ui.network.MemberDetailActivity
import com.redcodetechnologies.mlm.ui.network.adapter.DownMemberAdapter
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.LinearLayoutManagerWrapper
import java.util.ArrayList
import javax.security.auth.callback.Callback

class VideosListFragment : Fragment() {
    var adapter: VideosAdapter? = null
    var list: ArrayList<VideosModal> = ArrayList()
    private var recyclerView : RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {

        var view : View =inflater.inflate(R.layout.fragment_videos_list, container, false)

        initView(view)

        return view

    }
    private fun initView(view: View) {
        recyclerView = view.findViewById(R.id.recylcer_videos)
        recyclerView!!.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView!!.setLayoutManager(linearLayoutManager)
        (activity as DrawerActivity).getSupportActionBar()?.setTitle("Videos")
        (activity as DrawerActivity).getSupportActionBar()?.setIcon(0)

        recyclerView!!.layoutManager = LinearLayoutManagerWrapper(activity!!, LinearLayout.VERTICAL, false)
        adapter = VideosAdapter(activity!!, list) { obj ->
                var intent = Intent(activity!!, YoutubePlayerActivity::class.java)
                var json = Gson().toJson(obj)
                intent.putExtra("object", json)
                startActivity(intent)

            recyclerView!!.adapter = adapter
            recyclerView!!.setItemAnimator(null);

        }
        showViews()
    }

    fun showViews() {
            (activity as DrawerActivity).getSupportActionBar()?.setTitle("Videos")
            (activity as DrawerActivity).getSupportActionBar()?.setIcon(0)

    }
    fun getDownlineviewData() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }

        ApiClint.getInstance()?.getService()?.getVideosData("bearer ")
                ?.enqueue(object : Callback<VideosModal> {
                    override fun onFailure(call: Call<VideosModal>?, t: Throwable?) {
                        println("error")

                    }

                    override fun onResponse(call: Call<VideosModal>?, response: retrofit2.Response<VideosModal>?) {
                        print("object success ")
                        var code: Int = response!!.code()


                        if (code == 200) {
                            print("success")
                            var obj: VideosModal = response.body()!!

                            if (obj.TrainingVideoId != null)
                                tv_leftRemaingAmount!!.text = obj.leftRemaingAmount!!.split(".")[0];

                        }


                    }
                })
    }


    private fun generateDummyVideoList():ArrayList<VideosModal> {
        val youtubeVideoModelArrayList : ArrayList<VideosModal> = ArrayList()
        //get the video id array, title array and duration array from strings.xml
        val videoIDArray = getResources().getStringArray(R.array.video_id_array)
        val videoTitleArray = getResources().getStringArray(R.array.video_title_array)
        val videoDurationArray = getResources().getStringArray(R.array.video_duration_array)

        for (i in videoIDArray.indices)
        {
            val youtubeVideoModel = VideosModal()
            youtubeVideoModel.setVideoId(videoIDArray[i])
            youtubeVideoModel.setTitle(videoTitleArray[i])
            youtubeVideoModel.setDuration(videoDurationArray[i])
            youtubeVideoModelArrayList.add(youtubeVideoModel)
        }
        return youtubeVideoModelArrayList
    }
}