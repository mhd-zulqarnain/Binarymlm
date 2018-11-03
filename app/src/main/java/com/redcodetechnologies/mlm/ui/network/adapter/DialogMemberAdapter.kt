package com.redcodetechnologies.mlm.ui.network.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Users
import com.redcodetechnologies.mlm.utils.CustomNameSearch

class DialogMemberAdapter(var ctx: Context, var list: ArrayList<Users>) : RecyclerView.Adapter<DialogMemberAdapter.MyViewHolder>() {
    var customFilter: CustomNameSearch? = null


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_dialog_member_view, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, p1: Int) {
        holder.bindView(list[p1])

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_name: TextView? = null
        var tv_amount: TextView? = null
        var tv_sponser: TextView? = null


        fun bindView(users: Users) {
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_amount = itemView.findViewById(R.id.tv_amount)
            tv_sponser = itemView.findViewById(R.id.tv_sponser)
            tv_name!!.text = users.UserName
            tv_sponser!!.text = users.SponsorName
            if (users.PaidAmount != null)
                tv_amount!!.text = users.PaidAmount!!.split(".")[0]

        }
    }
}
