package com.redcodetechnologies.mlm.ui.support.adapter

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.telephony.PhoneNumberUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Inbox
import com.redcodetechnologies.mlm.utils.InboxSearch
import java.util.ArrayList


class InboxAdapter (var ctx: Context, var datalist: ArrayList<Inbox>, private val onItemClick: (Int, String) -> Unit): RecyclerView.Adapter<InboxAdapter.ViewHolder>(), Filterable {
    var inboxFilter: InboxSearch? = null
    var face : Typeface? = null

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
        face = ResourcesCompat.getFont(this.ctx , R.font.ralewayregular);
        ctx = parent.context
        return ViewHolder(v);
    }
    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val inbox: Inbox = datalist[position]
        holder?.Sender?.text = inbox.Sender_Name
        holder?.Date?.text = inbox.Date
        holder?.Status?.text = inbox.Status

        if (holder?.Status.text.equals("new")) {
            holder?.Sender.setTextColor(Color.parseColor("#09AF00"));
            holder?.Sender.setTypeface(holder?.Sender.getTypeface(), Typeface.BOLD)
            holder?.Date.setTextColor(Color.parseColor("#09AF00"));
            holder?.Date.setTypeface(holder?.Sender.getTypeface(), Typeface.BOLD)
        } else {
            holder?.Sender.setTextColor(Color.parseColor("#000000"));
            holder?.Sender.setTypeface(face, Typeface.NORMAL)
            holder?.Date.setTextColor(Color.parseColor("#000000"));
            holder?.Date.setTypeface(face, Typeface.NORMAL)
        }

        holder?.btn_reply.setOnClickListener() {
            holder?.Sender.setTextColor(Color.parseColor("#000000"));
            holder?.Sender.setTypeface(face, Typeface.NORMAL)
            holder?.Date.setTextColor(Color.parseColor("#000000"));
            holder?.Date.setTypeface(face, Typeface.NORMAL)
            holder?.Status.setText("read")
            onItemClick(position, "viewandreply")

        }
        holder?.btn_dlt.setOnClickListener() {
            //onItemClick(position,"delete")
            removeItem(inbox)
        }

    }
        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
            var Sender = itemView.findViewById(R.id.tv_User) as TextView
            var Date= itemView.findViewById(R.id.tv_date) as TextView
            var Status =itemView.findViewById(R.id.tv_status)as TextView
            var btn_reply= itemView.findViewById(R.id.btn_reply) as Button
            var btn_dlt= itemView.findViewById(R.id.btn_dlt) as Button
        }




    private fun removeItem(inbox: Inbox) {

        val currPosition = datalist.indexOf(inbox)
        datalist.removeAt(currPosition)
        notifyItemRemoved(currPosition)
    }

}

