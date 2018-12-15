package com.redcodetechnologies.mlm.ui.support.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Messages
import com.redcodetechnologies.mlm.models.Response
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.ui.support.MessageSearch
import com.redcodetechnologies.mlm.utils.SharedPrefs
import retrofit2.Call
import retrofit2.Callback
import java.util.ArrayList


class MessageAdapter(var ctx: Context, var datalist: ArrayList<Messages>, var type: String, private val onItemClick: (Messages) -> Unit) : RecyclerView.Adapter<MessageAdapter.ViewHolder>(), Filterable {
    var messageFilter: MessageSearch? = null
    var face: Typeface? = null
    public var context: Context = ctx

    val SPONSER_INBOX:String="Sponser_Inbox"
    val IT_INBOX:String="IT_Inbox"
    val SPONSER_SENT:String="Sponser_Sent"
    val IT_SENT:String="IT_Sent"

    override fun getFilter(): Filter {
        if (messageFilter == null)
            messageFilter = MessageSearch(datalist, this
            )
        else
            messageFilter
        return messageFilter!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.single_unread_msg_row, parent, false)
        face = ResourcesCompat.getFont(this.ctx, R.font.ralewayregular);
        ctx = parent.context

        return ViewHolder(v);

    }

    override fun getItemCount(): Int {
        return datalist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message: Messages = datalist[position]

        holder.bindView(datalist[position], type,ctx)

        holder.btn_dlt.setOnClickListener() {
            removeItem(message,type)
        }
        holder.btn_reply.setOnClickListener() {
            onItemClick(datalist[position])
        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var messagelabel = itemView.findViewById(R.id.message_label) as LinearLayout
        var Sender = itemView.findViewById(R.id.tv_User) as TextView
        var Date = itemView.findViewById(R.id.tv_date) as TextView
        var btn_reply = itemView.findViewById(R.id.btn_reply) as Button
        var btn_dlt = itemView.findViewById(R.id.btn_dlt) as Button



        fun bindView(messages: Messages, type: String, ctx: Context) {

            val sponserId = SharedPrefs.getInstance()!!.getUser(ctx).sponsorId



            if (messages.IsRead == false) {
                messagelabel.setBackgroundColor(Color.parseColor("#C9CBCF"));
                btn_reply.setBackgroundResource(R.drawable.button_circle_green)
                        Sender.setTextSize(14f)


            }

            if (messages.IsRead == true) {
                messagelabel.setBackgroundColor(Color.WHITE);
                btn_reply.setBackgroundResource(R.drawable.button_circle_blue)
                Sender.setTextSize(12f)
            }

            if (type == "IT_Sent" || type == "Sponser_Sent") {

                when (type) {
                    "Sponser_Sent" ->
                        if (messages.SponserId == 1) {
                            Sender.text = "Admin"
                        } else if(messages.SponserId == sponserId) {
                            Sender.text = "Sponser"
                        } else {
                            Sender.text = "Downliner"
                        }
                    "IT_Sent"->
                        Sender.text = "Support"
                }
            } else
                Sender.text = messages.Sender_Name

            Date.text = messages.CreateDate!!.split('T')[0]

        }

    }

    private fun removeItem(message: Messages, type: String) {

        val currPosition = datalist.indexOf(message)
        if(type==SPONSER_INBOX)
            deleteSponserinboxmsg(message, currPosition)
        else if(type==IT_INBOX)
            deleteinboxmsgitsupport(message, currPosition)
         else if(type==IT_SENT)
            deletereadmessageitsupport(message, currPosition)
        else if(type==SPONSER_SENT)
            deletereadmessagesponsorsupport(message, currPosition)


    }

    private fun deleteSponserinboxmsg(message: Messages, currPosition: Int) {

        if (!Apputils.isNetworkAvailable(ctx)) {
            Toast.makeText(ctx, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }


        ApiClint.getInstance()?.getService()?.deleteSponserinboxmsg(message.Id!!)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        Toast.makeText(ctx, " Network error ", Toast.LENGTH_SHORT).show()

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        if (code == 200) {
                            datalist.removeAt(currPosition)
                            notifyItemRemoved(currPosition)
                        }

                        if (code != 200) {

                            Toast.makeText(ctx, " Network error ", Toast.LENGTH_SHORT).show()
                        }

                    }
                })
    }

    private fun deleteinboxmsgitsupport(message: Messages, currPosition: Int) {

        if (!Apputils.isNetworkAvailable(ctx)) {
            Toast.makeText(ctx, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }


        ApiClint.getInstance()?.getService()?.deleteinboxmsgitsupport(message.Id!!)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        Toast.makeText(ctx, " Network error ", Toast.LENGTH_SHORT).show()

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        var status = response.body()!!.success
                        var msg = response.body()!!.message
                        if (code == 200) {
                            datalist.removeAt(currPosition)
                            notifyItemRemoved(currPosition)
                        }

                        if (code != 200) {

                            Toast.makeText(ctx, " Network error ", Toast.LENGTH_SHORT).show()
                        }

                    }
                })
    }

    private fun deletereadmessagesponsorsupport(message: Messages, currPosition: Int) {

        if (!Apputils.isNetworkAvailable(ctx)) {
            Toast.makeText(ctx, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }


        ApiClint.getInstance()?.getService()?.deletereadmessagesponsorsupport(message.Id!!)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        Toast.makeText(ctx, " Network error ", Toast.LENGTH_SHORT).show()

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        var status = response.body()!!.success
                        var msg = response.body()!!.message
                        if (code == 200) {
                            datalist.removeAt(currPosition)
                            notifyItemRemoved(currPosition)
                        }

                        if (code != 200) {

                            Toast.makeText(ctx, " Network error ", Toast.LENGTH_SHORT).show()
                        }

                    }
                })
    }

    private fun deletereadmessageitsupport(message: Messages, currPosition: Int) {

        if (!Apputils.isNetworkAvailable(ctx)) {
            Toast.makeText(ctx, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }


        ApiClint.getInstance()?.getService()?.deletereadmessageitsupport(message.Id!!)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        Toast.makeText(ctx, " Network error ", Toast.LENGTH_SHORT).show()

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        var status = response.body()!!.success
                        var msg = response.body()!!.message
                        if (code == 200) {
                            datalist.removeAt(currPosition)
                            notifyItemRemoved(currPosition)
                        }

                        if (code != 200) {

                            Toast.makeText(ctx, " Network error ", Toast.LENGTH_SHORT).show()
                        }

                    }
                })
    }

}

