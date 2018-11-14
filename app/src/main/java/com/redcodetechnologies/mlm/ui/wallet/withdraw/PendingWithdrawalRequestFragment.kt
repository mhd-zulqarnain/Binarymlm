package com.redcodetechnologies.mlm.ui.wallet.withdraw


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.wallet.EWalletWithdrawalFundModel
import com.redcodetechnologies.mlm.ui.wallet.adapter.WithdrawAdapter
import com.redcodetechnologies.mlm.models.wallet.WithdrawalRequestModal
import java.util.ArrayList

class PendingWDRequestFragment : Fragment() {
    var frgement_type = "MyPackageCommisionList"
    var recylcer_wd: RecyclerView? = null
    var adapter: WithdrawAdapter? = null
    var list: ArrayList<EWalletWithdrawalFundModel> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_withdrawlayout, container, false)
        frgement_type = arguments?.getString("Fragment").toString();
        initView(view)
        return view
    }
    private fun initView(view: View?) {
        list.add(EWalletWithdrawalFundModel("Ali", "ATM",
                "1200", "120", "15/12/2018"))
        list.add(EWalletWithdrawalFundModel("Shakoor", "Cheque",
                "1300", "130", "12/11/2018"))
        list.add(EWalletWithdrawalFundModel("Zulqarnain", "ATM",
                "1400", "140", "12/12/2018"))

        recylcer_wd = view!!.findViewById(R.id.recylcer_wd_request)
        recylcer_wd!!.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)
        adapter = WithdrawAdapter(activity!!, frgement_type, list)
        recylcer_wd!!.adapter = adapter
    }
}
