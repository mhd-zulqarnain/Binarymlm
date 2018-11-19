package com.redcodetechnologies.mlm.ui.wallet.withdraw


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast

import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.wallet.EWalletWithdrawalFundModel
import com.redcodetechnologies.mlm.models.wallet.TransactionModal
import com.redcodetechnologies.mlm.ui.wallet.adapter.WithdrawAdapter
import com.redcodetechnologies.mlm.models.wallet.WithdrawalRequestModal
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.utils.Apputils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class PendingWDRequestFragment : Fragment() {
    var frgement_type = "MyPackageCommisionList"
    var recylcer_wd: RecyclerView? = null
    var adapter: WithdrawAdapter? = null
    var list: ArrayList<EWalletWithdrawalFundModel> = ArrayList()
    var mothlyDisposable: Disposable? = null
    var wdList: ArrayList<WithdrawalRequestModal> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_withdrawlayout, container, false)
        frgement_type = arguments?.getString("Fragment").toString();
        initView(view)
        return view
    }
    private fun initView(view: View?) {
        list.add(EWalletWithdrawalFundModel("Ali", "ATM",
                "1200", "120", "15/12/2018"))
        list.add(EWalletWithdrawalFundModel("Shakoor", "Cheque",
                "1300", "130", "12/11/2018"))
        list.add(EWalletWithdrawalFundModel("Zulqarnain", "ATM",
                "1400", "140", "12/12/2018"))

        recylcer_wd = view!!.findViewById(R.id.recylcer_wd_request)
        recylcer_wd!!.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)
        adapter = WithdrawAdapter(activity!!, frgement_type, list)
        recylcer_wd!!.adapter = adapter
    }
    fun getPendingWithdrawalList() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, "Network error", Toast.LENGTH_SHORT).show()
            return
        }
        val thisMonthtransaction = getThisMonthObserver()
        val thismothObservable: Observable<ArrayList<WithdrawalRequestModal>> = MyApiRxClint.getInstance()!!.getService()!!.getPendingWdRequest()
        thismothObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(thisMonthtransaction)
    }
    fun getThisMonthObserver(): Observer<ArrayList<WithdrawalRequestModal>> {
        return object : Observer<ArrayList<WithdrawalRequestModal>> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable) {
                mothlyDisposable = d
            }

            override fun onNext(t: ArrayList<WithdrawalRequestModal>) {
                t?.forEach{tranactions->
                    wdList.add(tranactions)
                }
            }

            override fun onError(e: Throwable) {
                println("error")
            }
        }
    }
}
