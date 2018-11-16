package com.redcodetechnologies.mlm.ui.wallet.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.wallet.EWalletWithdrawalFundModel
import com.redcodetechnologies.mlm.models.wallet.WithdrawalRequestModal

class WithdrawAdapter (var ctx: Context, var type: String, var list: ArrayList<EWalletWithdrawalFundModel>) : RecyclerView.Adapter<WithdrawAdapter.MyViewHolder>() {

    var typ = type


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_row_withdraw, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.bindView(list[p1])
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_wd_username: TextView? = null
        var tv_wd_payment_method: TextView? = null
        var tv_wd_amount_payable: TextView? = null
        var tv_wd_charges: TextView? = null
        var tv_wd_date: TextView? = null

        fun bindView(eWalletWithdrawalFundModel: EWalletWithdrawalFundModel) {
            tv_wd_username = itemView.findViewById(R.id.wd_username)
            tv_wd_payment_method = itemView.findViewById(R.id.wd_pay_method)
            tv_wd_amount_payable = itemView.findViewById(R.id.wd_amount_pay)
            tv_wd_charges = itemView.findViewById(R.id.wd_charges)
            tv_wd_date = itemView.findViewById(R.id.wd_date)

            tv_wd_username!!.text = eWalletWithdrawalFundModel.UserName
            tv_wd_payment_method!!.text = eWalletWithdrawalFundModel.WithdrawalFundMethod
            tv_wd_amount_payable!!.text = eWalletWithdrawalFundModel.AmountPayble
            tv_wd_charges!!.text = eWalletWithdrawalFundModel.WithdrawalFundCharge
            tv_wd_date!!.text = eWalletWithdrawalFundModel.ApprovedDate

        }
    }
}

