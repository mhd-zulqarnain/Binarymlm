package com.redcodetechnologies.mlm.withdrawalrequestviewpager


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.adapter.PackageCommisionListAdapter
import com.redcodetechnologies.mlm.adapter.WithdrawAdapter
import com.redcodetechnologies.mlm.models.PackageCommisionList
import com.redcodetechnologies.mlm.models.WithdrawalRequestModal
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class ApprovedPaid : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_approved_paid, container, false)
    }




}
