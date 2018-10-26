package com.redcodetechnologies.mlm.adapter

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Inbox
import com.redcodetechnologies.mlm.utils.InboxSearch
import java.util.ArrayList


class InboxAdapter (var ctx: Context, var datalist: ArrayList<Inbox>, private val onItemClick: (Int, String) -> Unit): RecyclerView.Adapter<InboxAdapter.ViewHolder>(), Filterable {
    var inboxFilter: InboxSearch? = null

    override fun getFilter(): Filter {
        if (inboxFilter == null)
            inboxFilter = InboxSearch(datalist, this
            )
        else
            inboxFilter
        return inboxFilter!!
    }

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
        holder?.btn_view.setOnClickListener(){
            onItemClick(position,"view")
        }
        holder?.btn_reply.setOnClickListener(){
           onItemClick(position,"reply")
        }
        holder?.btn_dlt.setOnClickListener(){
            onItemClick(position,"delete")
        }
        }

        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var Sender = itemView.findViewById(R.id.tv_User) as TextView
            var Date= itemView.findViewById(R.id.tv_date) as TextView
            var Status =itemView.findViewById(R.id.tv_status)as TextView
            var btn_view= itemView.findViewById(R.id.btn_view) as Button
            var btn_reply= itemView.findViewById(R.id.btn_reply) as Button
            var btn_dlt= itemView.findViewById(R.id.btn_dlt) as Button
        }


    private fun showReply() {
        val view: View = LayoutInflater.from(ctx!!).inflate(R.layout.reply_dialoge, null)
        val alertBox = AlertDialog.Builder(ctx!!)
        alertBox.setView(view)
        alertBox.setCancelable(true)
        val dialog = alertBox.create()
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }

    private fun removeItem(inbox: Inbox) {

        val currPosition = datalist.indexOf(inbox)
        datalist.removeAt(currPosition)
        notifyItemRemoved(currPosition)
    }

}

