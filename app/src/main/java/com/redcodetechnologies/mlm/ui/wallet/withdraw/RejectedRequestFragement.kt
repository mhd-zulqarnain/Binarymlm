package com.redcodetechnologies.mlm.ui.wallet.withdraw


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.redcodetechnologies.mlm.R

class RejectedRequestFragement : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_withdrawlayout, container, false)
    }


}