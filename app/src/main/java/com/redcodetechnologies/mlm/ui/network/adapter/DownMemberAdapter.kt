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
import android.widget.LinearLayout
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.users.Users
import com.redcodetechnologies.mlm.utils.CustomNameSearch


class DownMemberAdapter(var ctx: Context, var type: String, var list: ArrayList<Users>, private val onClick: (Users) -> Unit) : RecyclerView.Adapter<DownMemberAdapter.MyViewHolder>(), Filterable {
    var typ = type
    var customFilter: CustomNameSearch? = null

    override fun getFilter(): Filter {
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

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: MyViewHolder, p1: Int) {
        holder.bindView(list[p1], typ,ctx)
        holder.itemView.setOnClickListener {
            onClick(list[p1])
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_name: TextView? = null
        var tv_phone: TextView? = null
        var tv_bank: TextView? = null
        var tv_sponser: TextView? = null
        var tv_paid: TextView? = null
        var card_members: CardView? = null

        fun bindView(users: Users, typ: String, ctx: Context) {


//            itemView.setClickable(true);

            tv_name = itemView.findViewById(R.id.tv_name)
            tv_phone = itemView.findViewById(R.id.tv_phone)
            card_members = itemView.findViewById(R.id.card_members)
            tv_bank = itemView.findViewById(R.id.tv_bank)
            tv_sponser = itemView.findViewById(R.id.tv_sponser)
            tv_paid = itemView.findViewById(R.id.tv_paid)
            if (typ == "DownlineMembers") {
                val img = ctx.getResources().getDrawable(R.drawable.ic_preview)
                tv_paid!!.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null)
            }
            if (typ == "MakeTable") {
                tv_sponser!!.layoutParams = LinearLayout.LayoutParams(0, 0, 0f)
                tv_name!!.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.35f)
                tv_paid!!.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.35f)
                tv_phone!!.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.9f)
                tv_bank!!.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.35f)
            }

            if (users.Username != null)
                tv_name!!.text = users.Username
            if (users.Phone != null)
                tv_phone!!.text = users.Phone
            if (users.BankName != null)
                tv_bank!!.text = users.BankName!!
            if (users.SponsorName != null)
                tv_sponser!!.text = users.SponsorName
            if (users.PaidAmount != null)
                tv_paid!!.text = users.PaidAmount!!.split(".")[0]
        }
    }
}
