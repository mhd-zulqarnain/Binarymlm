package com.redcodetechnologies.mlm.ui


import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.ui.support.adapter.NotificationAdapter
import com.redcodetechnologies.mlm.models.NotificationModal
import com.redcodetechnologies.mlm.models.VideosModal
import com.redcodetechnologies.mlm.ui.support.VideosAdapter
import java.util.ArrayList

class VideosListFragment : Fragment() {
    var frgement_type = "wallet_credits"
    var recycler_videos: RecyclerView? = null
    var adapter: VideosAdapter? = null
    var list: ArrayList<VideosModal> = ArrayList()
    var tv_header: TextView? = null
    var tv_name: TextView? = null
    var tv_desc: TextView? = null
    var tv_action: TextView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_videos_list, container, false)

        frgement_type = arguments?.getString("Fragment").toString();


        initView(view)
        return view

    }

    private fun initView(view: View?) {
        list.add(VideosModal("Funny", "done", "1000"))
        list.add(VideosModal("Entertainment", "Pending", "3000"))
        list.add(VideosModal("Account open", "done", "1000"))

        recycler_videos = view!!.findViewById(R.id.recylcer_videos)
        tv_header = view!!.findViewById(R.id.tv_notific_header)
        tv_name = view!!.findViewById(R.id.tv_notific_name)
        tv_desc = view!!.findViewById(R.id.tv_notific_desc)
        tv_action = view!!.findViewById(R.id.tv_notofic_action)

        recycler_videos!!.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)
        adapter = VideosAdapter(activity!!, frgement_type, list)
        recycler_videos!!.adapter = adapter


    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as DrawerActivity).getSupportActionBar()?.setTitle("Videos")
    }


}