package com.redcodetechnologies.mlm.ui.wallet.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.WalletModal

class WalletAdapter(var ctx: Context, var type: String, var list: ArrayList<WalletModal>) : RecyclerView.Adapter<WalletAdapter.MyViewHolder>() {

    var typ=type


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_row_wallet, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.bindView(list[p1],typ)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_source: TextView? = null
        var tv_name: TextView? = null
        var tv_amount: TextView? = null
        var tv_date: TextView? = null
        fun bindView(walletmodal : WalletModal, typ :String) {

            tv_source = itemView.findViewById(R.id.tv_walt_source)
            tv_name = itemView.findViewById(R.id.tv_walt_name)
            tv_amount = itemView.findViewById(R.id.tv_walt_amount)
            tv_date = itemView.findViewById(R.id.tv_walt_date)


            tv_source!!.text = walletmodal.walt_source
            tv_name!!.text = walletmodal.walt_name
            tv_amount!!.text = walletmodal.walt_amount
            tv_date!!.text = walletmodal.walt_date


            //  tv_price!!.text = order.BitPrice

        }
    }
}
