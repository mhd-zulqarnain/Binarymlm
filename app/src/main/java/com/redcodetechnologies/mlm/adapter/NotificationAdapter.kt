package com.redcodetechnologies.mlm.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.NotificationModal
import com.redcodetechnologies.mlm.models.WalletModal

class NotificationAdapter (var ctx: Context,  var type: String, var list: ArrayList<NotificationModal>) : RecyclerView.Adapter<NotificationAdapter.MyViewHolder>(){

    var typ=type

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_notification_list, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: NotificationAdapter.MyViewHolder, p1: Int) {
        p0.bindView(list[p1],typ)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_desc: TextView? = null
        var tv_name: TextView? = null
        var tv_action: TextView? = null
        fun bindView(notificationmodal : NotificationModal, typ :String) {

            tv_desc = itemView.findViewById(R.id.tv_notific_desc)
            tv_name = itemView.findViewById(R.id.tv_notific_name)
            tv_action = itemView.findViewById(R.id.tv_notofic_action)


            tv_name!!.text = notificationmodal.notific_name
            tv_desc!!.text = notificationmodal.notific_desc
            tv_action!!.text = notificationmodal.notific_action


            //  tv_price!!.text = order.BitPrice

        }
    }

}