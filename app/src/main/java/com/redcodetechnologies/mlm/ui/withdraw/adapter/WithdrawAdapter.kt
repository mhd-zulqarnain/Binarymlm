package com.redcodetechnologies.mlm.ui.withdraw.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.WithdrawalRequestModal

class WithdrawAdapter (var ctx: Context, var type: String, var list: ArrayList<WithdrawalRequestModal>) : RecyclerView.Adapter<WithdrawAdapter.MyViewHolder>() {

    var typ = type


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_row_withdraw, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.bindView(list[p1], typ)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_wd_username: TextView? = null
        var tv_wd_payment_method: TextView? = null
        var tv_wd_amount_payable: TextView? = null
        var tv_wd_charges: TextView? = null
        var tv_wd_date: TextView? = null

        fun bindView(withdrawalrequestmodal: WithdrawalRequestModal, typ: String) {
            tv_wd_username = itemView.findViewById(R.id.wd_username)
            tv_wd_payment_method = itemView.findViewById(R.id.wd_pay_method)
            tv_wd_amount_payable = itemView.findViewById(R.id.wd_amount_pay)
            tv_wd_charges = itemView.findViewById(R.id.wd_charges)
            tv_wd_date = itemView.findViewById(R.id.wd_date)

            tv_wd_username!!.text = withdrawalrequestmodal.wd_username
            tv_wd_payment_method!!.text = withdrawalrequestmodal.wd_paymen_method
            tv_wd_amount_payable!!.text = withdrawalrequestmodal.wd_payable
            tv_wd_charges!!.text = withdrawalrequestmodal.wd_charges
            tv_wd_date!!.text = withdrawalrequestmodal.wd_requested_date


        }
    }
}

