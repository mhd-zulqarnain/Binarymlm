package com.redcodetechnologies.mlm.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.adapter.InboxAdapter
import com.redcodetechnologies.mlm.models.Inbox
import java.util.*


class InboxFragment : Fragment() {
    var search_view: SearchView? = null
    var inboxAdapater: InboxAdapter? = null
    var inboxList: ArrayList<Inbox> = ArrayList()
    var inbox_recycler: RecyclerView? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view:View =inflater.inflate(R.layout.fragment_inbox, container, false)
        initView(view)
        return view
    }

    private fun initView(view:View) {

        var data = ArrayList<Inbox>()
        data.add(Inbox("Basit","How r u Admin","Yes","16/04.2018"))
        data.add(Inbox("Arif Bozdar","How r u Ali","Yes","15/04.2018"))
        data.add(Inbox("Shakoor","How r u Z.Baliti","Y","15/04.2018"))
        data.add(Inbox("Ali","How r u Arif","Yes","11/04.2018"))
        data.add(Inbox("Basit","How r u Admin","Yes","16/04.2018"))
        data.add(Inbox("Arif Bozdar","How r u Ali","Yes","15/04.2018"))
        data.add(Inbox("Shakoor","How r u Z.Baliti","Y","15/04.2018"))
        data.add(Inbox("Ali", "How r u Arif", "Yes", "11/04.2018"))

        search_view = view.findViewById(R.id.search_view)
        var recyclerView = view!!.findViewById(R.id.unread_inbox_recycler) as RecyclerView
        recyclerView.layoutManager= LinearLayoutManager(activity!!,LinearLayout.VERTICAL,false) as RecyclerView.LayoutManager?



        var report = InboxAdapter(activity!!,data){postion,type->
            if(type=="reply") {
                editmesseage(data[postion])
            }else if(type=="view"){
                viewmesseage(data[postion])
            }
        }
        /*{ onItemClick(post,type)
            openreportdialog(data[post])
        }*/
        recyclerView!!.adapter = report



        report.notifyDataSetChanged()

        search_view!!.setOnClickListener {
            search_view!!.setIconified(false)
        }

        search_view!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }
            override fun onQueryTextChange(query: String): Boolean {
                report!!.getFilter().filter(query)
                return false
            }
        })


    }

    private fun editmesseage(inbox: Inbox) {
        var v:View = LayoutInflater.from(activity!!).inflate(R.layout.reply_dialoge,null)
        val alertBox = android.support.v7.app.AlertDialog.Builder((activity as Context?)!!)
        alertBox.setView(v)
        var dialog = alertBox.create()
        dialog!!.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog!!.setCancelable(false);
        var message : EditText = v.findViewById(R.id.ed_message)
        var usser_view : TextView = v.findViewById(R.id.tx_user)
        var btn : Button = v.findViewById(R.id.btn_submit2)

        message.setText("")
        usser_view.text = inbox.Sender_Name

        btn!!.setOnClickListener{

            dialog!!.dismiss()
            // api call to send message
        }
        dialog!!.show()

    }

    fun viewmesseage(inbox:Inbox){

        var v:View = LayoutInflater.from(activity!!).inflate(R.layout.inbox_view_message,null)
        val alertBox = android.support.v7.app.AlertDialog.Builder((activity as Context?)!!)
        alertBox.setView(v)
        var dialog = alertBox.create()
        dialog!!.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog!!.setCancelable(false);
        var message : TextView = v.findViewById(R.id.message)
        var usser_view : TextView = v.findViewById(R.id.usser_view)
        var btn : Button = v.findViewById(R.id.btn_submit)

        message.text = inbox.Sender_Messege
        usser_view.text = inbox.Sender_Name

        btn!!.setOnClickListener{

            dialog!!.dismiss()
        }
        dialog!!.show()

    }




}
