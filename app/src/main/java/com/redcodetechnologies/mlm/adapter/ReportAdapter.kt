package com.redcodetechnologies.mlm.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Report
import com.redcodetechnologies.mlm.models.Users

class ReportAdapter(var ctx: Context, var datalist: ArrayList<Report>): RecyclerView.Adapter<ReportAdapter.MyViewHolder>() {
    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.bindView(datalist[p1])
    }

    var customFilter: CustomNameSearch? = null


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.report_dialog, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return datalist.size
    }



    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
/*
        var tv_name: TextView? = null
        var tv_phone: TextView? = null
        var tv_bank: TextView? = null
        var tv_account: TextView? = null
        var tv_paid: TextView? = null
*/
        fun bindView(users: Report) {

   /*         tv_name = itemView.findViewById(R.id.tv_name)
            tv_phone = itemView.findViewById(R.id.tv_phone)
            tv_bank = itemView.findViewById(R.id.tv_bank)
            tv_account = itemView.findViewById(R.id.tv_account)
            tv_paid = itemView.findViewById(R.id.tv_paid)
            tv_name!!.text = users.name
            tv_phone!!.text = users.phone
            tv_bank!!.text = users.bank
            tv_account!!.text = users.account
            tv_paid!!.text = users.paid
*/

            //  tv_price!!.text = order.BitPrice

        }
    }
}
