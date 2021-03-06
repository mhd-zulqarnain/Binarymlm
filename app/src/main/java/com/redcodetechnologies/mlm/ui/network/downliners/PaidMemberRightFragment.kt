package com.redcodetechnologies.mlm.ui.network.downliners


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.users.Users
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.ui.network.adapter.StatusAdapter
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.SharedPrefs
import dmax.dialog.SpotsDialog
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.ArrayList

class PaidMemberRightFragment : Fragment() {

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

    var search_view: SearchView? = null
    private var isViewShown = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_paid_unpaid_member, container, false)

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
        tv_total = view.findViewById(R.id.tv_total)
        recylcer_wd = view.findViewById(R.id.recylcer_down_member)
        recylcer_wd!!.layoutManager = LinearLayoutManager(activity!!, LinearLayout.VERTICAL, false)
        adapter = StatusAdapter(activity!!, wdList)

        recylcer_wd!!.adapter = adapter
        search_view = view.findViewById(R.id.search_view)
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

        getUsersData()
    }

    fun getUsersData() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Apputils.showMsg(activity!!, "Network error")
            return
        }
        progressdialog!!.show()
        if(!wdList.isEmpty()){
            wdList.clear()
        }
        val dataOberver = getDataOberver()
        val thismothObservable: Observable<ArrayList<Users>> = MyApiRxClint.getInstance()!!.getService()!!.getuserPaidmembersrightlist(id!!)
        thismothObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataOberver)
    }

    fun getDataOberver(): Observer<ArrayList<Users>> {
        return object : Observer<ArrayList<Users>> {
            var total: Double = 0.0

            override fun onComplete() {
                progressdialog!!.dismiss()
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
                    tv_total.setText("$total PKR ")

                }
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
            getUsersData()
        } else {
            isViewShown = false;
        }
    }


}
