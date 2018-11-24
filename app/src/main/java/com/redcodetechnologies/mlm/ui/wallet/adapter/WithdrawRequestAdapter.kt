package com.redcodetechnologies.mlm.ui.wallet.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.wallet.TransactionModal
import com.redcodetechnologies.mlm.models.wallet.WithdrawalRequestModal

class WithdrawRequestAdapter(var ctx: Context, var list: ArrayList<WithdrawalRequestModal>) : RecyclerView.Adapter<WithdrawRequestAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_row_wallet, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.bindView(list[p1])
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_source: TextView? = null
        var tv_name: TextView? = null
        var tv_amount: TextView? = null
        var tv_date: TextView? = null
        fun bindView(model : WithdrawalRequestModal) {

            tv_source = itemView.findViewById(R.id.tv_walt_source)
            tv_name = itemView.findViewById(R.id.tv_walt_name)
            tv_amount = itemView.findViewById(R.id.tv_walt_amount)
            tv_date = itemView.findViewById(R.id.tv_walt_date)


            tv_source!!.text = "test source"
            tv_name!!.text ="test name"
            tv_amount!!.text ="1000"
            tv_date!!.text = "10-3-2123"


            //  tv_price!!.text = order.BitPrice

        }
    }
}
