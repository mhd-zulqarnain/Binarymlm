package com.redcodetechnologies.mlm.ui.wallet.withdraw

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerTabStrip
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.ui.wallet.adapter.ViewPagerAdapterWD

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

