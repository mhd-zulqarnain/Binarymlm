package com.redcodetechnologies.mlm.ui.dashboard

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Advertisement
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.ui.dashboard.adapter.AdvertismentAdapter
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.SharedPrefs
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SleepingDashboardFragment : Fragment() {
    var tv: CardView?=null
    var click:Boolean=true;
    var adsdisposable: Disposable? = null
    var progressBar: LinearLayout? = null
    var ads_view: LinearLayout? = null
    lateinit var prefs: SharedPrefs
    var recycler_adds: RecyclerView? = null
    var GetEWalletSummarySponsorBonus: TextView? = null

    var adsList: ArrayList<Advertisement> = ArrayList()
    var adapter: AdvertismentAdapter? = null

    var id: Int? = null
    var token: String?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view  =inflater.inflate(R.layout.fragment_sleeping_dashboard, container, false)
        tv = view.findViewById(R.id.dashboardbalance) as CardView;
        recycler_adds = view.findViewById(R.id.recylcer_adds)
        ads_view = view.findViewById(R.id.ads_view)
        GetEWalletSummarySponsorBonus = view.findViewById(R.id.GetEWalletSummarySponsorBonus)
        prefs = SharedPrefs.getInstance()!!

        if (prefs.getUser(activity!!).userId != null) {
            id = prefs.getUser(activity!!).userId
            token = prefs.getToken(activity!!).accessToken!!
        }

        progressBar = view.findViewById(R.id.progressBar)
        adapter=AdvertismentAdapter(activity!!, "", adsList)
        val manager = LinearLayoutManager(activity!!, LinearLayout.HORIZONTAL, false)
        recycler_adds!!.layoutManager = manager
        recycler_adds!!.adapter = adapter

        tv!!.setOnClickListener{
            if(click){
                if(GetEWalletSummarySponsorBonus!!.visibility!=View.VISIBLE)
                showBalanaceDialog(view)
            }
        }

        getads()
        return view
    }


    private fun showBalanaceDialog(view: View) {

        val view: View = LayoutInflater.from(activity!!).inflate(R.layout.dialogue_forget_password, null)
        val alertBox = android.support.v7.app.AlertDialog.Builder(activity!!)
        alertBox.setView(view)
        alertBox.setCancelable(true)
        val dialog = alertBox.create()
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        var tvtitle: TextView = view.findViewById(R.id.tvtitle)
        var tvdescription: TextView = view.findViewById(R.id.tvdescription)
        tvtitle.text = "Enter Password"
        tvdescription.layoutParams = LinearLayout.LayoutParams(10, 10, 1.75f)
        tvdescription.text = ""

        var ed_email_address: EditText = view.findViewById(R.id.ed_email)
        ed_email_address.visibility = View.GONE
        var pass: EditText = view.findViewById(R.id.ed_pass)
        pass.visibility = View.VISIBLE
        var button_submit: Button = view.findViewById(R.id.btn_submit)

        button_submit.setOnClickListener {
            if (pass.text.toString().trim(' ').length < 1) {
                pass.error = Html.fromHtml("<font color='#E0796C'>Password cant be null</font>")
                pass.requestFocus()
            } else {
                val password = prefs.getUser(activity!!).password
                if (pass.text.toString() == password) {
                    GetEWalletSummarySponsorBonus!!.visibility = View.VISIBLE
                    dialog.dismiss()
                } else {
                    Toast.makeText(activity!!, "Wrong-Password", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()

            }
        }

        dialog.show()

    }

    //<editor-fold desc="Advertisment control">
    private fun getads() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, "Network error", Toast.LENGTH_SHORT).show()
            return
        }

        progressBar!!.visibility = View.VISIBLE
        ads_view!!.visibility = View.GONE
        val adsObserver = getadvertismentObserver()
        var adsObservable: Observable<ArrayList<Advertisement>> = MyApiRxClint.getInstance()?.getService()?.getCoinData()!!
        adsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adsObserver)
    }

    private fun getadvertismentObserver(): Observer<ArrayList<Advertisement>> {
        return object : Observer<ArrayList<Advertisement>> {
            override fun onComplete() {
                println("completed")
                progressBar!!.visibility = View.GONE
                ads_view!!.visibility = View.VISIBLE

            }

            override fun onSubscribe(d: Disposable) {
                adsdisposable = d

            }

            override fun onError(e: Throwable) {
                progressBar!!.visibility = View.VISIBLE
                Toast.makeText(activity!!, "Network error", Toast.LENGTH_SHORT).show()

            }

            override fun onNext(response: ArrayList<Advertisement>) {

                response?.forEach { ads ->
                    adsList.add(ads)
                }
                adapter!!.notifyDataSetChanged()
            }

        }
    }
    //</editor-fold>
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as DrawerActivity).getSupportActionBar()!!.setTitle("Dashboard")
    }

    override fun onDestroyView() {
        adsdisposable?.dispose()
        super.onDestroyView()
    }

}
