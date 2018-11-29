package com.redcodetechnologies.mlm.ui.notification

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.MyNotification
import com.redcodetechnologies.mlm.models.NotificationModal

class NotificationAdapter (var ctx: Context, var list: ArrayList<MyNotification>) : RecyclerView.Adapter<NotificationAdapter.MyViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_notification_list, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.bindView(list[p1])
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_desc: TextView? = null
        var tv_name: TextView? = null
        fun bindView(notificationmodal : MyNotification) {

            tv_desc = itemView.findViewById(R.id.tv_notific_desc)
            tv_name = itemView.findViewById(R.id.tv_notific_name)


            tv_name!!.text = notificationmodal.NotificationName
            tv_desc!!.text = notificationmodal.NotificationDescription


            //  tv_price!!.text = order.BitPrice

        }
    }

}