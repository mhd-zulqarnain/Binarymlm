
package com.redcodetechnologies.mlm.ui.profile.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.redcodetechnologies.mlm.ui.profile.UpdatePrivacyFragment
import com.redcodetechnologies.mlm.ui.profile.UpdateProfileFragment

class ViewPagerAdapter internal constructor(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val COUNT = 2

    override fun getItem(position: Int): Fragment? {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = UpdateProfileFragment()
            1 -> fragment = UpdatePrivacyFragment()
        }

        return fragment
    }

    override fun getCount(): Int {
        return COUNT
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var  title = ""
        if (position == 0)
            title= "Profile Settings "
        if(position == 1)
            title =  "Privacy Settings"
        return title


    }


}