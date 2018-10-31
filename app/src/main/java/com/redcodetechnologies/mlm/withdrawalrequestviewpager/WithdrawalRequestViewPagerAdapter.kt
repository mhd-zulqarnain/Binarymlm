package com.redcodetechnologies.mlm.withdrawalrequestviewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.mir.viewpager.FirstFragment
import com.example.mir.viewpager.SecondFragment

class ViewPagerAdapterWD internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val COUNT = 4

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = PendingWDRequestFragment()
            1 -> fragment = ApprovedPendingPayment()
            2 -> fragment = ApprovedPaid()
            3 -> fragment = RejectedRequests()
        }

        return fragment
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var  title = ""
        if (position == 0)
            title= "Pending Withdrawal Requests"
        if(position == 1)
            title =  "Approved - Pending Payment"
        if(position == 2)
            title =  "Approved - Paid"
        if(position == 3)
            title =  "Rejected Requests"
        return title



    }

  

}