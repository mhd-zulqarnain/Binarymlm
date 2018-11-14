package com.redcodetechnologies.mlm.ui.wallet

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.ui.wallet.adapter.WalletAdapter
import com.redcodetechnologies.mlm.models.wallet.TransactionModal
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.utils.Apputils
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class TransactionFragment : Fragment() {

    var frgement_type = "wallet_credits"
    var recylcer_down: RecyclerView? = null
    var adapter: WalletAdapter? = null
    var transactionList: ArrayList<TransactionModal> = ArrayList()
    var tv_header: TextView? = null
    var tv_source: TextView? = null
    var tv_name: TextView? = null
    var tv_amount: TextView? = null
    var transaction_filter_group: RadioGroup? = null
    var month_limit_filter: RadioButton? = null
    var overall_price_filter: RadioButton? = null
    var tv_date: TextView? = null
    var progressBar: LinearLayout? = null
    var overAllDisposable: Disposable? = null
    var mothlyDisposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_transaction, container, false)

        frgement_type = arguments?.getString("Fragment").toString();
        initView(view)
        return view
    }

    private fun initView(view: View?) {

        transactionList.add(TransactionModal())
        transactionList.add(TransactionModal())
        transactionList.add(TransactionModal())

        recylcer_down = view!!.findViewById(R.id.recylcer_walt_down)
        tv_header = view.findViewById(R.id.tv_walt_header)
        tv_source = view.findViewById(R.id.tv_walt_source)
        tv_name = view.findViewById(R.id.tv_walt_name)
        tv_amount = view.findViewById(R.id.tv_walt_amount)
        tv_date = view.findViewById(R.id.tv_walt_date)
        transaction_filter_group = view.findViewById(R.id.transaction_filter_group)
        month_limit_filter = view.findViewById(R.id.month_limit_filter)
        overall_price_filter = view.findViewById(R.id.overall_price_filter)
        progressBar = view.findViewById(R.id.progressBar)

        recylcer_down!!.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)
        adapter = WalletAdapter(activity!!, frgement_type, transactionList)
        recylcer_down!!.adapter = adapter


        transaction_filter_group!!.setOnCheckedChangeListener(
                RadioGroup.OnCheckedChangeListener { group, checkId ->
                    if (month_limit_filter!!.isChecked ) {
                        transactionList.clear()
                        if(overAllDisposable!=null)
                            overAllDisposable!!.dispose()
                        if(frgement_type=="wallet_transactions"){
//                        getThisMonthTransactionList()
                        }else if(frgement_type=="wallet_credits"){
//                        getThisMonthEWalletCreditList()
                        }else if(frgement_type=="wallet_debits"){
//                        getThisMonthEWalletDebitList()
                        }

                    }
                    if (overall_price_filter!!.isChecked) {
                        transactionList.clear()
                        if(mothlyDisposable!=null)
                            mothlyDisposable!!.dispose()
                        if(frgement_type=="wallet_transactions"){
                            //getOverAllTransactionList()
                        }else if(frgement_type=="wallet_credits"){
//                        getOverAllEWalletCreditList()
                        }else if(frgement_type=="wallet_debits"){
//                        getOverAllEWalletDebittList()
                        }

                    }
                })

        showViews()
    }

    fun showViews() {
        if (frgement_type == "wallet_credits") {
            tv_header!!.text = " E-wallet Credits"
        } else if (frgement_type == "wallet_transactions") {
            tv_header!!.text = " E-wallet Transactions"
        } else if (frgement_type == "wallet_debits") {
            tv_header!!.text = " E-wallet Debits"
        }

    }

    //<editor-fold desc="Transactions">
    fun getOverAllTransactionList() {
        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, "Network error", Toast.LENGTH_SHORT).show()
            return
        }

        progressBar!!.visibility = View.VISIBLE
        val transactionObserver = getOverAllObserver()
        var transactionObservable: Observable<ArrayList<TransactionModal>> = MyApiRxClint.getInstance()!!.getService()!!.getOverAllTransation()
        transactionObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(transactionObserver)

    }

    fun getThisMonthTransactionList() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, "Network error", Toast.LENGTH_SHORT).show()
            return
        }
        progressBar!!.visibility = View.VISIBLE
        val thisMonthtransaction = getThisMonthObserver()
        val thismothObservable: Observable<ArrayList<TransactionModal>> = MyApiRxClint.getInstance()!!.getService()!!.getMonthlyTransation()
        thismothObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(thisMonthtransaction)
    }


    //</editor-fold>

    //<editor-fold desc="E wallet debit">
    fun getOverAllEWalletDebittList() {
        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, "Network error", Toast.LENGTH_SHORT).show()
            return
        }

        progressBar!!.visibility = View.VISIBLE
        val transactionObserver = getOverAllObserver()
        var transactionObservable: Observable<ArrayList<TransactionModal>> = MyApiRxClint.getInstance()!!.getService()!!.getOverAllEWalletDebit()
        transactionObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(transactionObserver)

    }

    fun getThisMonthEWalletDebitList() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, "Network error", Toast.LENGTH_SHORT).show()
            return
        }
        progressBar!!.visibility = View.VISIBLE
        val thisMonthtransaction = getThisMonthObserver()
        val thismothObservable: Observable<ArrayList<TransactionModal>> = MyApiRxClint.getInstance()!!.getService()!!.getMonthlyEWalletDebit()
        thismothObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(thisMonthtransaction)
    }
    //</editor-fold>

    //<editor-fold desc="E wallet Credit">
    fun getOverAllEWalletCreditList() {
        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, "Network error", Toast.LENGTH_SHORT).show()
            return
        }

        progressBar!!.visibility = View.VISIBLE
        val transactionObserver = getOverAllObserver()
        var transactionObservable: Observable<ArrayList<TransactionModal>> = MyApiRxClint.getInstance()!!.getService()!!.getOverAllEWalletCredit()
        transactionObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(transactionObserver)

    }

    fun getThisMonthEWalletCreditList() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, "Network error", Toast.LENGTH_SHORT).show()
            return
        }
        progressBar!!.visibility = View.VISIBLE
        val thisMonthtransaction = getThisMonthObserver()
        val thismothObservable: Observable<ArrayList<TransactionModal>> = MyApiRxClint.getInstance()!!.getService()!!.getMonthlyEWalletCredit()
        thismothObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(thisMonthtransaction)
    }
    //</editor-fold>

    //<editor-fold desc="Observers">
    fun getOverAllObserver(): Observer<ArrayList<TransactionModal>> {
        return object : Observer<ArrayList<TransactionModal>> {
            override fun onComplete() {
                progressBar!!.visibility = View.VISIBLE
            }

            override fun onSubscribe(d: Disposable) {
                overAllDisposable = d
            }

            override fun onNext(t: ArrayList<TransactionModal>) {
                t.forEach{transaction->
                    transactionList.add(transaction)
                }

            }

            override fun onError(e: Throwable) {
                println("error")
            }

        }
    }

    fun getThisMonthObserver(): Observer<ArrayList<TransactionModal>> {
        return object : Observer<ArrayList<TransactionModal>> {
            override fun onComplete() {
                progressBar!!.visibility = View.GONE
            }

            override fun onSubscribe(d: Disposable) {
                mothlyDisposable = d
            }

            override fun onNext(t: ArrayList<TransactionModal>) {
                t?.forEach{tranactions->
                    transactionList.add(tranactions)
                }
            }

            override fun onError(e: Throwable) {
                println("error")
            }
        }
    }
    //</editor-fold>

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as DrawerActivity).getSupportActionBar()?.setTitle("E-Wallet")
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

}
