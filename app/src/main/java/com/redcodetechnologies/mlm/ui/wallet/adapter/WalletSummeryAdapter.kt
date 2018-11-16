package com.redcodetechnologies.mlm.ui.wallet.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.wallet.WalletSummery
import kotlin.collections.ArrayList

class WalletSummeryAdapter (var ctx: Context,var wlist: ArrayList<WalletSummery>):
        RecyclerView.Adapter<WalletSummeryAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindview(wlist[position])

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = ViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_row_walletsummery, parent, false))

        return v;
    }
    override fun getItemCount(): Int {
        return wlist.size
    }
        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var Catagory:TextView?=null
           var Balance:TextView?=null

            private val ViewHolder.textView: TextView?
                get() = itemView.findViewById(R.id.balance)

            fun bindview(wallet: WalletSummery) {

                Catagory = itemView.findViewById(R.id.catagory)
                Balance= itemView.findViewById(R.id.balance)

                Catagory!!.text= wallet.Catagory
                Balance!!.text= wallet.Balance
            }

            }
}

