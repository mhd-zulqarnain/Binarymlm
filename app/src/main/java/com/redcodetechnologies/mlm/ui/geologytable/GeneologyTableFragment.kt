package com.redcodetechnologies.mlm.ui.geologytable

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Response
import com.redcodetechnologies.mlm.models.wallet.TransactionModal
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.SharedPrefs
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import java.util.ArrayList

class GeneologyTableFragment : Fragment() {
    var frgement_type = "MyPackageCommisionList"
    var recylcer_down: RecyclerView? = null
    var adapter: PackageCommisionListAdapter? = null
    var commitionlist: ArrayList<TransactionModal> = ArrayList()

    lateinit var tv_action: TextView
    lateinit var tv_header: TextView
    lateinit var tv_source: TextView
    lateinit var tv_name: TextView
    lateinit var tv_amount: TextView
    lateinit var tv_date: TextView
    lateinit var tv_no_data: LinearLayout
    lateinit var txt_warning: LinearLayout
    lateinit var progressBar: LinearLayout

    var pref = SharedPrefs.getInstance();
    var id: Int? = null
    var comissionDisposable: Disposable? = null

    val MY_PKG_COMMISTION_LIST = "MyPackageCommisionList"
    val MY_DIRECT_COMMISTION_LIST = "MyDirectCommisionList"
    val MY_TABLE_COMMISTION_LIST = "MyTableCommisionList"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_geneologytable, container, false)
        frgement_type = arguments?.getString("Fragment").toString();
        id = pref!!.getUser(activity!!).userId
        initView(view)
        return view
    }

    private fun initView(view: View) {

        recylcer_down = view.findViewById(R.id.recylcer_down)
        tv_action = view.findViewById(R.id.tv_tran_action)
        tv_header = view.findViewById(R.id.tv_header)
        tv_no_data = view.findViewById(R.id.tv_no_data)
        tv_source = view.findViewById(R.id.tv_tran_source)
        tv_name = view.findViewById(R.id.tv_tran_name)
        tv_amount = view.findViewById(R.id.tv_tran_amount)
        tv_date = view.findViewById(R.id.tv_tran_date)
        txt_warning = view.findViewById(R.id.txt_warning)
        progressBar = view.findViewById(R.id.progressBar)
        showViews()
    }

    fun showViews() {

        recylcer_down!!.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)
        adapter = PackageCommisionListAdapter(activity!!, frgement_type, commitionlist) { obj ->
            //perform action
            if (frgement_type == MY_DIRECT_COMMISTION_LIST || frgement_type == MY_TABLE_COMMISTION_LIST)
                senddirectsalecommissionrequest(obj.TransactionId!!)
            else
                sendmatchingtablecommissionrequest(obj.TransactionId!!)
        }

        if (!pref!!.getUser(activity!!).isVerify!!) {
            txt_warning.visibility = View.VISIBLE
        }
        recylcer_down!!.adapter = adapter

        if (frgement_type == MY_PKG_COMMISTION_LIST) {
            tv_action.visibility = View.GONE
            tv_name.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.75f)
            tv_source.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.75f)
            tv_amount.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.75f)
            tv_date.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.75f)
            tv_action.layoutParams = LinearLayout.LayoutParams(0, 0, 0f)
            tv_header.text = "Package Commision List"
            tv_date.setText("Transaction Date")
            tv_name.setText("Transaction Name")
            getPackageCommisionList()
            (activity as DrawerActivity).getSupportActionBar()?.setTitle("Package Commision List")
        } else if (frgement_type == MY_DIRECT_COMMISTION_LIST || frgement_type == MY_TABLE_COMMISTION_LIST) {
            tv_action.visibility = View.VISIBLE


            if (frgement_type == MY_TABLE_COMMISTION_LIST) {
                tv_header.text = "Table Commision List"
                getMyTableCommsionList()
            } else {
                tv_header.text = "Sale Commission List"
                getMyDirectCommsionList()

            }
            (activity as DrawerActivity).getSupportActionBar()?.setTitle("Direct Commision List")
            tv_date.setText("Payment Status")
            tv_name.setText("Request Status")

        }
        (activity as DrawerActivity).getSupportActionBar()?.setIcon(0)
    }

    fun getPackageCommisionList() {
        if (!Apputils.isNetworkAvailable(activity!!)) {
            Apputils.showMsg(activity!!, "Network error")
            return
        }

        if (commitionlist.isEmpty()) {
            commitionlist.clear()
        }

        progressBar.visibility = View.VISIBLE
        val commisionObserver = getCommisionObserver()
        val transactionObservable: Observable<ArrayList<TransactionModal>> = MyApiRxClint.getInstance()!!.getService()!!.getMyPackageComission(id!!)
        transactionObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commisionObserver)

    }

    fun getMyDirectCommsionList() {
        if (!Apputils.isNetworkAvailable(activity!!)) {
            Apputils.showMsg(activity!!, "Network error")
            return
        }
        if (commitionlist.isEmpty()) {
            commitionlist.clear()
        }

        progressBar.visibility = View.VISIBLE
        val commisionObserver = getCommisionObserver()
        val transactionObservable: Observable<ArrayList<TransactionModal>> = MyApiRxClint.getInstance()!!.getService()!!.getMyDirectCommsionList(id!!)
        transactionObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commisionObserver)

    }

    fun getMyTableCommsionList() {
        if (!Apputils.isNetworkAvailable(activity!!)) {
            Apputils.showMsg(activity!!, "Network error")
            return
        }
        if (commitionlist.isEmpty()) {
            commitionlist.clear()
        }

        progressBar.visibility = View.VISIBLE
        val commisionObserver = getCommisionObserver()
        val transactionObservable: Observable<ArrayList<TransactionModal>> = MyApiRxClint.getInstance()!!.getService()!!.getMyTableCommsionList(id!!)
        transactionObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(commisionObserver)

    }

    fun getCommisionObserver(): Observer<ArrayList<TransactionModal>> {
        return object : Observer<ArrayList<TransactionModal>> {
            override fun onComplete() {
                progressBar.visibility = View.GONE
            }

            override fun onSubscribe(d: Disposable) {
                comissionDisposable = d
            }

            override fun onNext(t: ArrayList<TransactionModal>) {
                t.forEach { transaction ->
                    commitionlist.add(transaction)
                }
                adapter!!.notifyDataSetChanged()
                if (t.size == 0) {
                    progressBar.visibility = View.GONE
                    recylcer_down!!.visibility = View.GONE
                    tv_no_data.visibility = View.VISIBLE
                }
            }

            override fun onError(e: Throwable) {
                println("error")
            }

        }
    }

    private fun sendmatchingtablecommissionrequest(id: Int) {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }

        if(!commitionlist.isEmpty()){
            commitionlist.clear()
        }
        progressBar.visibility = View.VISIBLE

        ApiClint.getInstance()?.getService()?.sendmatchingtablecommissionrequest(id)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        progressBar.visibility = View.GONE
                        Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()

                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        if (code == 200) {
                            getMyTableCommsionList()
                            Toast.makeText(activity!!, " Request sent ", Toast.LENGTH_SHORT).show()
                        }

                        if (code != 200) {
                            Toast.makeText(activity!!, " Failed ", Toast.LENGTH_SHORT).show()

                        }
                        showViews()

                    }
                })
    }

    private fun senddirectsalecommissionrequest(id: Int) {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }

        if(!commitionlist.isEmpty()){
            commitionlist.clear()
        }
        progressBar.visibility = View.VISIBLE

        ApiClint.getInstance()?.getService()?.senddirectsalecommissionrequest(id)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                        print("object success ")
                        var code: Int = response!!.code()
                        if (code == 200) {
                            Toast.makeText(activity!!, " Request sent  ", Toast.LENGTH_SHORT).show()
                        }

                        if (code != 200) {
                            Toast.makeText(activity!!, " Failed ", Toast.LENGTH_SHORT).show()

                        }
                        showViews()


                    }
                })
    }


    override fun onDestroyView() {
        if (comissionDisposable != null)
            comissionDisposable!!.dispose()
        super.onDestroyView()
    }


}
