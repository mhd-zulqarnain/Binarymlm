package com.redcodetechnologies.mlm.ui.wallet.withdraw


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.wallet.EWalletWithdrawalFundModel
import com.redcodetechnologies.mlm.models.wallet.WithdrawalRequestModal
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.utils.Apputils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class ApprovedPendingPaymentFragment : Fragment() {
    var list: ArrayList<EWalletWithdrawalFundModel> = ArrayList()
    var mothlyDisposable: Disposable? = null
    var wdlist: ArrayList<WithdrawalRequestModal> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_withdrawlayout, container, false)
    }

    fun getPendingWithdrawalList() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, "Network error", Toast.LENGTH_SHORT).show()
            return
        }
        val thisMonthtransaction = getThisMonthObserver()
        val thismothObservable: Observable<ArrayList<WithdrawalRequestModal>> = MyApiRxClint.getInstance()!!.getService()!!.getApprovedPendingWdRequest()
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
                    wdlist.add(tranactions)
                }
            }

            override fun onError(e: Throwable) {
                println("error")
            }
        }
    }



}
