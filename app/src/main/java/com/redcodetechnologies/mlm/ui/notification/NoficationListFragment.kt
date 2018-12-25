package com.redcodetechnologies.mlm.ui.notification

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.MyNotification
import com.redcodetechnologies.mlm.models.NotificationModal
import com.redcodetechnologies.mlm.models.wallet.TransactionModal
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.ServiceError
import com.redcodetechnologies.mlm.utils.ServiceListener
import com.redcodetechnologies.mlm.utils.SharedPrefs
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class NoficationListFragment : Fragment() {
    var frgement_type = "wallet_credits"
    var recylcer_notification: RecyclerView? = null
    var adapter: NotificationAdapter? = null
    var list: ArrayList<MyNotification> = ArrayList()
    var tv_name: TextView? = null
    var tv_desc: TextView? = null
    lateinit var progressBar: LinearLayout
    lateinit var tv_no_data: LinearLayout
    var disposable: Disposable? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_notification_list, container, false)

        frgement_type = arguments?.getString("Fragment").toString();


        initView(view)
        return view

    }
    private fun initView(view: View?) {

        recylcer_notification = view!!.findViewById(R.id.recylcer_notification)
        tv_name = view.findViewById(R.id.tv_notific_name)
        tv_desc = view.findViewById(R.id.tv_notific_desc)
        progressBar = view.findViewById(R.id.progressBar)
        tv_no_data = view.findViewById(R.id.tv_no_data)

        recylcer_notification!!.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)
        adapter = NotificationAdapter(activity!!, list){obj->
            //action
        }
        recylcer_notification!!.adapter = adapter

        getNotifications()

    }

    fun getNotifications(){
        if (!Apputils.isNetworkAvailable(activity!!)) {
            Apputils.showMsg(activity!!, "Network error")
            return
        }
        progressBar.visibility = View.VISIBLE

        val observer =getObserver()
        val userId = SharedPrefs.getInstance()!!.getUser(activity!!).userId
        val observable: Observable<ArrayList<MyNotification>> = MyApiRxClint.getInstance()!!.getService()!!.getNotificationList(userId!!)

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)
    }


    private fun showNotificationDialog(obj :NotificationModal) {

        val view: View = LayoutInflater.from(activity!!).inflate(R.layout.dialog_notification, null)
        val alertBox = android.support.v7.app.AlertDialog.Builder(activity!!)
        alertBox.setView(view)
        alertBox.setCancelable(false)
        val dialog = alertBox.create()
        //dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        val tvtitle: TextView = view.findViewById(R.id.tv_title)
        val tvdescription: TextView = view.findViewById(R.id.tv_des)
        tvtitle.setText(obj.notific_name)
        tvdescription.setText(obj.notific_desc)
        val btn_dismiss: Button = view.findViewById(R.id.btn_dismiss)

        btn_dismiss.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }


    fun getObserver(): Observer<ArrayList<MyNotification>> {

        return object: Observer<ArrayList<MyNotification>>{
            override fun onComplete() {
                progressBar.visibility = View.GONE

            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onNext(t: ArrayList<MyNotification>) {

                t.forEach{obj->
                    list.add(obj)
                }
                adapter!!.notifyDataSetChanged()
                if(t.size==0){
                    progressBar.visibility = View.GONE
                    recylcer_notification!!.visibility = View.GONE
                    tv_no_data.visibility=View.VISIBLE
                }
            }

            override fun onError(e: Throwable) {
                print("error")
            }
        }
    }
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as DrawerActivity).getSupportActionBar()?.setTitle("Notifications")
        (activity as DrawerActivity).getSupportActionBar()?.setIcon(R.drawable.ic_notifications_black_24dp)
    }
    override fun onDestroyView() {
        if (disposable != null)
            disposable!!.dispose()
        super.onDestroyView()
    }


}