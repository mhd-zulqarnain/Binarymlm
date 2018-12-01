package com.redcodetechnologies.mlm.ui.videos


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.VideosModal
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import java.util.ArrayList

class VideosListFragment : Fragment() {

    private var recyclerView : RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                               savedInstanceState: Bundle?): View? {

        var view : View =inflater.inflate(R.layout.fragment_videos_list, container, false)

        initView(view)
        populateRecyclerView()

        return view

    }
    private fun initView(view: View) {
        recyclerView = view.findViewById(R.id.recylcer_videos)
        recyclerView!!.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView!!.setLayoutManager(linearLayoutManager)
        (activity as DrawerActivity).getSupportActionBar()?.setTitle("Videos")
        (activity as DrawerActivity).getSupportActionBar()?.setIcon(0)
    }

    private fun populateRecyclerView() {
        val youtubeVideoModelArrayList = generateDummyVideoList()
        val adapter = VideosAdapter(activity!!, youtubeVideoModelArrayList)
        recyclerView!!.setAdapter(adapter)
        recyclerView!!.addOnItemTouchListener(RecyclerViewOnClickListener(activity!!, object : RecyclerViewOnClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                //start youtube player activity by passing selected video id via intent
                startActivity(Intent(activity, YoutubePlayerActivity::class.java)
                        .putExtra("video_id", youtubeVideoModelArrayList.get(position).getVideoId()))
            }
        }))
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