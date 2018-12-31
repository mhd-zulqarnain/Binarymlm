package com.redcodetechnologies.mlm.ui.wallet

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerTabStrip
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.ui.wallet.adapter.ViewPagerAdapterWD

class MyWithdrawalRequestFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_mywithdrawalrequest, container, false)
        (activity as DrawerActivity).getSupportActionBar()?.setTitle("Withdrawal Request")
        val viewPager = view.findViewById<ViewPager>(R.id.viewPagerwd)
        val strip = view.findViewById<PagerTabStrip>(R.id.pager_headerwd)
        strip.setTabIndicatorColor(Color.WHITE);
        strip.setPadding(-200,0,0,0)
        val adapter = ViewPagerAdapterWD(activity!!.supportFragmentManager)
        viewPager.adapter = adapter
        return view

    }
}

