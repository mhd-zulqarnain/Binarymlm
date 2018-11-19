package com.redcodetechnologies.mlm.ui.wallet

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity

class WithdrawalFundFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_withdrawalfund, container, false)
        (activity as DrawerActivity).getSupportActionBar()?.setTitle("Wallet Withdrawal Fund")
        // initView(view)
        return view

    }
}
