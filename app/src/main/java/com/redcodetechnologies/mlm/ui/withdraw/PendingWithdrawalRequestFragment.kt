package com.redcodetechnologies.mlm.ui.withdraw


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.ui.withdraw.adapter.WithdrawAdapter
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
class PendingWDRequestFragment : Fragment() {
    var frgement_type = "MyPackageCommisionList"
    var recylcer_wd: RecyclerView? = null
    var adapter: WithdrawAdapter? = null
    var list: ArrayList<WithdrawalRequestModal> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_pending_wdrequest, container, false)
        frgement_type = arguments?.getString("Fragment").toString();
        initView(view)
        return view

    }
    private fun initView(view: View?) {
        list.add(WithdrawalRequestModal("Ali","ATM", "1200", "120", "15/12/2018"))
        list.add(WithdrawalRequestModal("Shakoor","Cheque", "1300", "130", "12/11/2018"))
        list.add(WithdrawalRequestModal("Zulqarnain","ATM", "1400", "140", "12/12/2018"))
        recylcer_wd = view!!.findViewById(R.id.recylcer_pendingwd_request)

        recylcer_wd!!.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)
        adapter = WithdrawAdapter(activity!!, frgement_type, list)
        recylcer_wd!!.adapter = adapter


    }


}
