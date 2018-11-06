package com.redcodetechnologies.mlm.ui.dashboard

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redcodetechnologies.mlm.R
import android.app.Activity
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.widget.*
import com.redcodetechnologies.mlm.models.Advertisement
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.ui.auth.SignInActivity
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.ui.dashboard.adapter.AdvertismentAdapter
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.SharedPrefs
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import io.reactivex.Observable;
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers;


class DashBoardFragment : Fragment() {
    var frgement_type = "MyPackageCommisionList"
    var tv: CardView? = null
    var click: Boolean = true;
    lateinit var prefs: SharedPrefs

    var recycler_adds: RecyclerView? = null
    var tv_show_ads: TextView? = null
    var adsList: ArrayList<Advertisement> = ArrayList()
    var adapter: AdvertismentAdapter? = null
    var progressdialog: android.app.AlertDialog? = null
     var adsdisposable: Disposable? = null
    var progressBar: LinearLayout? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        prefs = SharedPrefs.getInstance()!!

        tv = view.findViewById(R.id.dashboardbalance) as CardView;
        progressdialog = SpotsDialog.Builder()
                .setContext(activity!!)
                .setMessage("Loading please wait!!")
                .setTheme(R.style.CustomProgess)
                .build()

        recycler_adds = view.findViewById(R.id.recylcer_adds)
        tv_show_ads = view.findViewById(R.id.tv_show_ads)
        progressBar = view.findViewById(R.id.progressBar)

        val manager = GridLayoutManager(activity!!, 2)
        recycler_adds!!.layoutManager = manager
        adapter = AdvertismentAdapter(activity!!, frgement_type, adsList)

        recycler_adds!!.adapter = adapter
        tv!!.setOnClickListener {
            if (click) {
                showSendDialog(view)
            }
        }
        getads()
        return view

    }


    private fun showSendDialog(v1: View) {
        val view: View = LayoutInflater.from(activity!!).inflate(R.layout.dialogue_forget_password, null)
        val alertBox = android.support.v7.app.AlertDialog.Builder(activity!!)
        alertBox.setView(view)
        alertBox.setCancelable(false)
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
                Toast.makeText(activity!!, "Under Process", Toast.LENGTH_SHORT).show()

                var sucess: Boolean = false;

                sucess = true;
                if (sucess) {
                    var tv_wallet_balance: TextView = v1.findViewById(R.id.tv_wallet_balance)
                    tv_wallet_balance.visibility = View.VISIBLE
                    click = false
                } else {
                    Toast.makeText(activity!!, "Wrong-Password", Toast.LENGTH_SHORT).show()

                }
                dialog.dismiss()

            }
        }

        dialog.show()

    }

    private fun getads() {

          if (!Apputils.isNetworkAvailable(activity!!)) {
           Toast.makeText(activity!!, "Network error", Toast.LENGTH_SHORT).show()
       }

        progressBar!!.visibility = View.VISIBLE
        val adsObserver = getadvertismentObservable()
        var adsObservable :Observable<ArrayList<Advertisement>>  = MyApiRxClint.getInstance()?.getService()?.getCoinData()!!
         adsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adsObserver)
    }

       private fun getadvertismentObservable(): Observer<ArrayList<Advertisement>> {
           return object : Observer<ArrayList<Advertisement>> {
               override fun onComplete() {
                   println("completed")
                   progressBar!!.visibility = View.GONE

               }
               override fun onSubscribe(d: Disposable) {
                   adsdisposable =d

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

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as DrawerActivity).getSupportActionBar()!!.setTitle("Dashboard")
    }

    override fun onDestroyView() {
        adsdisposable?.dispose()
        super.onDestroyView()
    }

}
