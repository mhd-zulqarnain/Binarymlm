package com.redcodetechnologies.mlm.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.WalletSummery

class EWalletSummaryFragment() : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_ewalletsummary, container, false)
        // initView(view)
        return view

    }

    private fun initView(view: View) {

        var wData = ArrayList<WalletSummery>()
        wData.add(WalletSummery("Bonus", "10PKR"))
        wData.add(WalletSummery("WithDrawl", "20PKR"))
        var tv_bonus: TextView? = null
        var tv_debit: TextView? = null
        tv_bonus = view!!.findViewById(R.id.wallet_Bcurrency)
        tv_debit = view!!.findViewById(R.id.wallet_Dcurrency)

        tv_bonus!!.setText(wData.get(1).Balance.toString())
    }
}


