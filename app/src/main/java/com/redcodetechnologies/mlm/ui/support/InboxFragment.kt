package com.redcodetechnologies.mlm.ui.support

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.*
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.ui.support.adapter.InboxAdapter
import com.redcodetechnologies.mlm.models.Inbox
import com.redcodetechnologies.mlm.models.Messages
import com.redcodetechnologies.mlm.models.MyNotification
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.SharedPrefs
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class InboxFragment : Fragment() {
    var search_view: SearchView? = null
    //    var inboxAdapater: InboxAdapter? = null
    var data: ArrayList<Messages> = ArrayList()
//    var inbox_recycler: RecyclerView? = null
    var adapter: InboxAdapter? = null
    var disposable: Disposable? = null
    lateinit var progressBar: LinearLayout
    lateinit var tv_no_data: LinearLayout
    lateinit var recyclerView:RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_inbox, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View) {


        search_view = view.findViewById(R.id.search_view)
        progressBar = view.findViewById(R.id.progressBar)
        tv_no_data = view.findViewById(R.id.tv_no_data)

         recyclerView = view.findViewById(R.id.unread_inbox_recycler) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false) as RecyclerView.LayoutManager?

        adapter = InboxAdapter(activity!!, data) { postion, type ->
            if (type == "viewandreply") {
                viewAndReply(data[postion])
            }
        }
        /*{ onItemClick(post,type)
            openreportdialog(data[post])
        }*/
        recyclerView.adapter = adapter
        adapter!!.notifyDataSetChanged()
        getMessages()
        search_view!!.setOnClickListener {
            search_view!!.setIconified(false)
        }
        search_view!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                adapter!!.getFilter().filter(query)
                return false
            }
        })
    }

    fun getMessages(){
        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, "Network error", Toast.LENGTH_SHORT).show()
            return
        }
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE

        val observer =getObserver()
        val userId = SharedPrefs.getInstance()!!.getUser(activity!!).userId
        val observable: Observable<ArrayList<Messages>> = MyApiRxClint.getInstance()!!.getService()!!.viewallmessagesupport(userId!!)

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }

    fun getObserver(): Observer<ArrayList<Messages>> {

        return object: Observer<ArrayList<Messages>> {
            override fun onComplete() {
                progressBar.visibility = View.GONE

            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onNext(t: ArrayList<Messages>) {

                t.forEach{obj->
                    data.add(obj)
                }
                adapter!!.notifyDataSetChanged()
                if(t.size==0){
                    progressBar.visibility = View.GONE
                    recyclerView.visibility = View.GONE
                    tv_no_data.visibility=View.VISIBLE
                }else{
                    recyclerView.visibility= View.VISIBLE
                }
            }

            override fun onError(e: Throwable) {
                print("error")
            }
        }
    }

    fun viewAndReply(inbox: Messages) {

        val v: View = LayoutInflater.from(activity!!).inflate(R.layout.inbox_view_message, null)
        val alertBox = android.support.v7.app.AlertDialog.Builder((activity as Context?)!!)
        alertBox.setView(v)
        val dialog = alertBox.create()
        dialog.window.setBackgroundDrawableResource(android.R.color.transparent);

        dialog!!.setCancelable(true);
        val message: TextView = v.findViewById(R.id.message)
        val usser_view: TextView = v.findViewById(R.id.usser_view)
        val btn_submit: Button = v.findViewById(R.id.btn_submit)

        message.text = inbox.Message
        usser_view.text = inbox.Sender_Name

        btn_submit.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as DrawerActivity).getSupportActionBar()!!.setTitle("Inbox")
        (activity as DrawerActivity).getSupportActionBar()!!.setIcon(R.drawable.ic_message)
    }
}
