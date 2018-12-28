package com.redcodetechnologies.mlm.ui.wallet.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.redcodetechnologies.mlm.ui.wallet.withdraw.ApprovedPaidFragment
import com.redcodetechnologies.mlm.ui.wallet.withdraw.ApprovedPendingPaymentFragment
import com.redcodetechnologies.mlm.ui.wallet.withdraw.PendingWDRequestFragment
import com.redcodetechnologies.mlm.ui.wallet.withdraw.RejectedRequestFragement

class ViewPagerAdapterWD internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val COUNT = 4

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = PendingWDRequestFragment()
            1 -> fragment = ApprovedPendingPaymentFragment()
            2 -> fragment = ApprovedPaidFragment()
            3 -> fragment = RejectedRequestFragement()
        }

        return fragment
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var  title = ""
        if (position == 0)
            title= "Pending "
        if(position == 1)
            title =  "Approved"
        if(position == 2)
            title =  "Paid"
        if(position == 3)
            title =  "Rejected "
        return title


    }

  

}