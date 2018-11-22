package com.redcodetechnologies.mlm.ui.network.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.users.UserTree

class PaidUnpaidAdapter(var ctx: Context, var type: String, var list: ArrayList<UserTree>) : RecyclerView.Adapter<PaidUnpaidAdapter.MyViewHolder>() {

    var typ = type
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.singlepaidunpaid, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.bindView(list[p1])

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_name: TextView? = null
        var tv_amount: TextView? = null
        var tv_sponsor: TextView? = null


        fun bindView(downlinermodal: UserTree) {
            tv_name = itemView.findViewById(R.id.paid_unpaid_name)
            tv_amount = itemView.findViewById(R.id.paid_unpaid_amount)
            tv_sponsor = itemView.findViewById(R.id.paid_unpaid_sponsor)


//            tv_name!!.text = downlinermodal.Name
//            tv_sponsor!!.text = downlinermodal.SponsorName
//            tv_amount!!.text = downlinermodal.PaidAmount


        }
    }
}
