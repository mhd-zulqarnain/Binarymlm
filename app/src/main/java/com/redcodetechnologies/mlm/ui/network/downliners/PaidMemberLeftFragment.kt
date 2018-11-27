package com.redcodetechnologies.mlm.ui.network.downliners


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
import com.redcodetechnologies.mlm.models.users.Users
import com.redcodetechnologies.mlm.models.wallet.WithdrawalRequestModal
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.ui.network.adapter.StatusAdapter
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class PaidMemberLeftFragment : Fragment() {

    var disposable: Disposable? = null
    var wdList: ArrayList<Users> = ArrayList()
    lateinit var prefs: SharedPrefs
    var id: Int? = null
    lateinit var token: String
    lateinit var tv_no_data: LinearLayout
    var progressdialog: android.app.AlertDialog? = null
    var recylcer_wd: RecyclerView? = null
    var adapter: StatusAdapter? = null
    lateinit var tv_total: TextView
    var total: Double = 0.0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_paid_member_left, container, false)


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

        return view
    }

    private fun initView(view: View?) {
        tv_no_data = view!!.findViewById(R.id.tv_no_data)
        tv_total = view!!.findViewById(R.id.tv_total)
        recylcer_wd = view!!.findViewById(R.id.recylcer_down_member)
        recylcer_wd!!.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)
        adapter = StatusAdapter(activity!!, wdList)
        recylcer_wd!!.adapter = adapter

        getUsersData()
    }

    fun getUsersData() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, "Network error", Toast.LENGTH_SHORT).show()
        }



        fun getDataOberver(): Observer<ArrayList<Users>> {
            return object : Observer<ArrayList<Users>> {
                override fun onComplete() {
                    progressdialog!!.hide()
                }

                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onNext(t: ArrayList<Users>) {
                    t.forEach { users ->
                        wdList.add(users)
                        total += users.PaidAmount!!.toDouble()
                    }
                    adapter!!.notifyDataSetChanged()
                    if (t.size == 0) {
                        tv_no_data.visibility = View.VISIBLE
                        tv_total.setText("0 PKR (0 PKR Total)")
                    } else {
                        tv_no_data.visibility = View.GONE
                        tv_total.setText("$total PKR ($total PKR Total)")
                    }
                }

                override fun onError(e: Throwable) {
                    println("error")
                }
            }
        }

    }}
