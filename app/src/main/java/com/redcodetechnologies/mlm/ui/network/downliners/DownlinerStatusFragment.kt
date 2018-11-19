package com.redcodetechnologies.mlm.ui.network.downliners


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerTabStrip
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.ui.network.adapter.DownlinerAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class DownlinerStatusFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_downliner_status, container, false)
        val viewPager = view!!.findViewById<ViewPager>(R.id.viewPagerdw)
        val strip = view!!.findViewById<PagerTabStrip>(R.id.pager_headerdw)
        strip.setPadding(-200,0,0,0)
        val adapter = DownlinerAdapter(activity!!.supportFragmentManager)
        viewPager.adapter = adapter
        (activity as DrawerActivity).getSupportActionBar()!!.setTitle("Paid UnPaid Downliners")
        return view
    }


}
