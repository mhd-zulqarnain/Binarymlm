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
import com.redcodetechnologies.mlm.models.users.Users


class StatusAdapter(var ctx: Context, var list: ArrayList<Users>) : RecyclerView.Adapter<StatusAdapter.MyViewHolder>(), Filterable {
    var customFilter: DirectMemberSearch? = null

    override fun getFilter(): Filter {
        if (customFilter == null)
            customFilter = DirectMemberSearch(list, this
            )
        else
            customFilter

        return customFilter!!
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_row_status, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.bindView(list[p1])
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_username: TextView? = null
        var tv_sponername : TextView? = null
        var tv_amount: TextView? = null
        fun bindView(model : Users) {

            tv_username = itemView.findViewById(R.id.tv_username)
            tv_amount = itemView.findViewById(R.id.tv_amount)
            tv_sponername = itemView.findViewById(R.id.tv_sponername)


            tv_username!!.text = model.Username
            tv_amount!!.text = model.PaidAmount
            tv_sponername!!.text = model.SponsorName

            //  tv_price!!.text = order.BitPrice

        }
    }
}
