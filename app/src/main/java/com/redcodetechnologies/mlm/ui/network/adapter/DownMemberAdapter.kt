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

class DownMemberAdapter(var ctx: Context, var list: ArrayList<Users> ,private val onClick:(Int)->Unit) : RecyclerView.Adapter<DownMemberAdapter.MyViewHolder>(), Filterable {
    var customFilter: CustomNameSearch? = null

       override fun getFilter(): Filter{
        if (customFilter == null)
            customFilter = CustomNameSearch(list, this
            )
        else
            customFilter

            return customFilter!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_downline_member_view, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, p1: Int) {
        holder.bindView(list[p1])
        holder.itemView.setOnClickListener {
            onClick(p1)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_name: TextView? = null
        var tv_phone: TextView? = null
        var tv_bank: TextView? = null
        var tv_sponser: TextView? = null
        var tv_paid: TextView? = null

        fun bindView(users: Users) {
            tv_name = itemView.findViewById(R.id.tv_name)
            tv_phone = itemView.findViewById(R.id.tv_phone)
            tv_bank = itemView.findViewById(R.id.tv_bank)
            tv_sponser = itemView.findViewById(R.id.tv_sponser)
            tv_paid = itemView.findViewById(R.id.tv_paid)
            tv_name!!.text = users.UserName
            tv_phone!!.text = users.Phone
            tv_bank!!.text = users.BankName
            tv_sponser!!.text = users.SponsorName
            tv_paid!!.text = users.PaidAmount
        }
    }
}
