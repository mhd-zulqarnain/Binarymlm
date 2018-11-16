package com.redcodetechnologies.mlm.ui.network.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.redcodetechnologies.mlm.ui.network.downliners.PaidMemberLeftFragment
import com.redcodetechnologies.mlm.ui.network.downliners.PaidMemberRightFragment
import com.redcodetechnologies.mlm.ui.network.downliners.UnPaidMemberLeftFragment
import com.redcodetechnologies.mlm.ui.network.downliners.UnPaidMemberRightFragment
import com.redcodetechnologies.mlm.ui.wallet.withdraw.ApprovedPaidFragment
import com.redcodetechnologies.mlm.ui.wallet.withdraw.ApprovedPendingPaymentFragment
import com.redcodetechnologies.mlm.ui.wallet.withdraw.PendingWDRequestFragment
import com.redcodetechnologies.mlm.ui.wallet.withdraw.RejectedRequestFragement

class DownlinerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val COUNT = 4

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = PaidMemberLeftFragment()
            1 -> fragment = UnPaidMemberLeftFragment()
            2 -> fragment = PaidMemberRightFragment()
            3 -> fragment = UnPaidMemberRightFragment()
        }

        return fragment
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var  title = ""
        if (position == 0)
            title= "Paid Members Left"
        if(position == 1)
            title =  "UnPaid Members Left"
        if(position == 2)
            title =  "Paid Members Right"
        if(position == 3)
            title =  "UnPaid Members Right"
        return title



    }



}