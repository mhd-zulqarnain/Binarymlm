package com.redcodetechnologies.mlm.ui.wallet.withdraw


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
import com.redcodetechnologies.mlm.models.wallet.WithdrawalRequestModal
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.ui.wallet.adapter.WithdrawRequestAdapter
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.SharedPrefs
import dmax.dialog.SpotsDialog
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class PendingWDRequestFragment : Fragment() {

    var mothlyDisposable: Disposable? = null
    var wdList: ArrayList<WithdrawalRequestModal> = ArrayList()
    lateinit var prefs: SharedPrefs
    var id: Int? = null
    lateinit var token: String
    lateinit var tv_no_data: LinearLayout
    var progressdialog: android.app.AlertDialog? = null
    var recylcer_wd: RecyclerView? = null
    var adapter: WithdrawRequestAdapter? = null
    lateinit var tv_req_type: TextView
    private var isViewShown = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_withdrawlayout, container, false)

        if (!isViewShown) {

            prefs = SharedPrefs.getInstance()!!
            if (prefs.getUser(activity!!).userId != null) {
                id = prefs.getUser(activity!!).userId
                token = prefs.getToken(activity!!).accessToken!!
            }
            progressdialog = SpotsDialog.Builder()
                    .setContext(activity!!)
                    .setMessage("Loading!!")
                    .setTheme(R.style.CustomProgess)
                    .build()
            initView(view)
        }
        return view
    }

    private fun initView(view: View) {
        tv_no_data = view.findViewById(R.id.tv_no_data)
        recylcer_wd = view.findViewById(R.id.recylcer_wd_request)
        recylcer_wd!!.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)
        adapter = WithdrawRequestAdapter(activity!!, wdList)
        recylcer_wd!!.adapter = adapter
        tv_req_type = view.findViewById(R.id.tv_req_type)
        tv_req_type.setText("Requested Date:")

        getPendingWithdrawalList()
    }

    fun getPendingWithdrawalList() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Apputils.showMsg(activity!!, "Network error")
            return
        }
        progressdialog!!.show()
        if(!wdList.isEmpty()){
            wdList.clear()
        }

        val thisMonthtransaction = getThisMonthObserver()
        val thismothObservable: Observable<ArrayList<WithdrawalRequestModal>> = MyApiRxClint.getInstance()!!.getService()!!.getPendingWdRequest(id!!)
        thismothObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(thisMonthtransaction)
    }

    fun getThisMonthObserver(): Observer<ArrayList<WithdrawalRequestModal>> {
        return object : Observer<ArrayList<WithdrawalRequestModal>> {
            override fun onComplete() {
                progressdialog!!.dismiss()
            }

            override fun onSubscribe(d: Disposable) {
                mothlyDisposable = d
            }

            override fun onNext(t: ArrayList<WithdrawalRequestModal>) {
                t.forEach { tranactions ->
                    wdList.add(tranactions)
                }
                adapter!!.notifyDataSetChanged()
                if (t.size == 0) {
                    tv_no_data.visibility = View.VISIBLE
                } else
                    tv_no_data.visibility = View.GONE

            }

            override fun onError(e: Throwable) {
                println("error")
            }
        }
    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (getView() != null) {
            isViewShown = true;
            getPendingWithdrawalList()
        } else {
            isViewShown = false;
        }
    }

}
