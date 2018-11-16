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
import java.util.ArrayList

class NoficationListFragment : Fragment() {

    var frgement_type = "wallet_credits"
    var recylcer_notification: RecyclerView? = null
    var adapter: NotificationAdapter? = null
    var list: ArrayList<NotificationModal> = ArrayList()
    var tv_header: TextView? = null
    var tv_name: TextView? = null
    var tv_desc: TextView? = null
    var tv_action: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_notification_list, container, false)

        frgement_type = arguments?.getString("Fragment").toString();
        initView(view)
        return view

    }
    private fun initView(view: View?) {

        list.add(NotificationModal("Account open", "done", "1000"))
        list.add(NotificationModal("Acount Close", "Pending", "3000"))
        list.add(NotificationModal("Account open", "done", "1000"))

        recylcer_notification = view!!.findViewById(R.id.recylcer_notification)
        tv_header = view.findViewById(R.id.tv_notific_header)
        tv_name = view.findViewById(R.id.tv_notific_name)
        tv_desc = view.findViewById(R.id.tv_notific_desc)
        tv_action = view.findViewById(R.id.tv_notofic_action)

        recylcer_notification!!.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)
        adapter = NotificationAdapter(activity!!, frgement_type, list)
        recylcer_notification!!.adapter = adapter

    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as DrawerActivity).getSupportActionBar()?.setTitle("Notifications")
    }

}