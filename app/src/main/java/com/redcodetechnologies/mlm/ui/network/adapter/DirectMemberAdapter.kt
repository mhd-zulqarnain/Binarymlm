package com.redcodetechnologies.mlm.ui.network.adapter

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.users.Users
import com.redcodetechnologies.mlm.utils.CustomNameSearch


class DirectMemberAdapter(var ctx: Context, var list: ArrayList<Users>, var frgement_type:String) : RecyclerView.Adapter<DirectMemberAdapter.MyViewHolder>()/*, Filterable*/ {
    //var customFilter: CustomNameSearch? = null

      /* override fun getFilter(): Filter{
        if (customFilter == null)
            customFilter = CustomNameSearch(list, this
            )
        else
            customFilter

            return customFilter!!
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_direct_member_view, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: MyViewHolder, p1: Int) {
        holder.bindView(list[p1])

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_amount: TextView? = null
        var tv_username: TextView? = null


        fun bindView(users: Users) {

            tv_amount = itemView.findViewById(R.id.tv_amount)
            tv_username = itemView.findViewById(R.id.tv_username)

            if(users.Username!=null)
                tv_username!!.text = users.Username

            if(users.PaidAmount!=null)
                tv_amount!!.text = users.PaidAmount!!.split(".")[0]
        }
    }
}
