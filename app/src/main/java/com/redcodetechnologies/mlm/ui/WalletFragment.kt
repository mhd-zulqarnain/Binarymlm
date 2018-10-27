package com.redcodetechnologies.mlm.ui

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.redcodetechnologies.mlm.DrawerActivity
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.adapter.PackageCommisionListAdapter
import com.redcodetechnologies.mlm.adapter.WalletAdapter
import com.redcodetechnologies.mlm.models.PackageCommisionList
import com.redcodetechnologies.mlm.models.WalletModal

import java.util.ArrayList

class WalletFragment : Fragment(){
    var frgement_type = "wallet_credits"
    var recylcer_down: RecyclerView? = null
    var adapter: WalletAdapter? = null
    var list: ArrayList<WalletModal> = ArrayList()
    var tv_header: TextView? = null
    var tv_source: TextView? = null
    var tv_name: TextView? = null
    var tv_amount: TextView? = null
    var tv_date: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_wallet, container, false)
        frgement_type = arguments?.getString("Fragment").toString();
        initView(view)
        return view
    }
    private fun initView(view: View?) {
        list.add(WalletModal("Yaseen00423", "Debit", "1000", "25-10-2018"))
        list.add(WalletModal("Ismail", "Debit", "3000", "27-10-2018"))
        list.add(WalletModal("AB Shakoor", "Debit", "900000", "23-10-2018"))
        recylcer_down = view!!.findViewById(R.id.recylcer_walt_down)
        tv_header = view!!.findViewById(R.id.tv_walt_header)
        tv_source = view!!.findViewById(R.id.tv_walt_source)
        tv_name = view!!.findViewById(R.id.tv_walt_name)
        tv_amount = view!!.findViewById(R.id.tv_walt_amount)
        tv_date = view!!.findViewById(R.id.tv_walt_date)

        recylcer_down!!.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)
        adapter = WalletAdapter(activity!!,frgement_type, list)
        recylcer_down!!.adapter = adapter



        showViews()
    }
    fun showViews() {
        if (frgement_type == "wallet_credits") {
            tv_header!!.text = " E-wallet Credits"
        }
        else if(frgement_type == "wallet_transactions"){
            tv_header!!.text = " E-wallet Transactions"
        }

        else if (frgement_type == "wallet_debits") {
            tv_header!!.text = " E-wallet Debits"
        }

    }
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as DrawerActivity).getSupportActionBar()?.setTitle("E-Wallet")
    }



}
