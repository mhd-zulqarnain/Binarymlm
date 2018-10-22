package com.redcodetechnologies.mlm.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Inbox


class InboxAdapter (var datalist: ArrayList<Inbox>): RecyclerView.Adapter<InboxAdapter.ViewHolder>() {

    var  ctx: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_unread_msg_row,parent,false)
        ctx = parent.context
        return ViewHolder(v);
    }
    override fun getItemCount(): Int {

        return datalist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val inbox : Inbox = datalist[position]
        holder?.Sender?.text= inbox.Sender_Name
        holder?.Date?.text= inbox.Date
        holder?.Status?.text= inbox.Status


        }

        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var Sender = itemView.findViewById(R.id.tv_User) as TextView
            var Date= itemView.findViewById(R.id.tv_date) as TextView
            var Status =itemView.findViewById(R.id.tv_status)as TextView
            var btn_view= itemView.findViewById(R.id.btn_view) as Button
            var btn_reply= itemView.findViewById(R.id.btn_reply) as Button
            var btn_dlt= itemView.findViewById(R.id.btn_dlt) as Button
        }



}

