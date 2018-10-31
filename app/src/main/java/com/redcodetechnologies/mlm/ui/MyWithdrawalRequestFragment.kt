package com.redcodetechnologies.mlm.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerTabStrip
import android.support.v4.view.ViewPager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.adapter.PackageCommisionListAdapter
import com.redcodetechnologies.mlm.models.PackageCommisionList
import com.redcodetechnologies.mlm.withdrawalrequestviewpager.ViewPagerAdapterWD
import java.util.ArrayList


class MyWithdrawalRequestFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_mywithdrawalrequest, container, false)



        val viewPager = view.findViewById<ViewPager>(R.id.viewPagerwd)
        val strip = view.findViewById<PagerTabStrip>(R.id.pager_headerwd)
        strip.setPadding(-200,0,0,0)


        val adapter = ViewPagerAdapterWD(activity!!.supportFragmentManager)
        viewPager.adapter = adapter


        return view

    }
}

