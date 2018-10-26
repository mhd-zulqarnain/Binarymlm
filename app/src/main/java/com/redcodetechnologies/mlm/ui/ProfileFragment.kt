package com.redcodetechnologies.mlm.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerTabStrip
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mir.viewpager.ViewPagerAdapter
import com.redcodetechnologies.mlm.R
import kotlinx.android.synthetic.main.fragment_profile.*

import android.support.v7.app.AppCompatActivity

class ProfileFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        var view = inflater.inflate(R.layout.fragment_profile, container, false)
        //initView(view)




       val viewPager = view.findViewById<ViewPager>(R.id.viewPager)
        val strip = view.findViewById<PagerTabStrip>(R.id.pager_header)
        strip.setPadding(-400,0,0,0)


         val adapter = ViewPagerAdapter(activity!!.supportFragmentManager)
            viewPager.adapter = adapter


        return view

    }
}
