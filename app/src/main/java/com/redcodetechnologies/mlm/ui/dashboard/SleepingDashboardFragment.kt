package com.redcodetechnologies.mlm.ui.dashboard

import android.app.Activity
import android.graphics.Bitmap
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
import com.redcodetechnologies.mlm.models.DasboardData
import com.redcodetechnologies.mlm.models.users.NewUserRegistration
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.ui.dashboard.adapter.AdvertismentAdapter
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.SharedPrefs
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback

class SleepingDashboardFragment : Fragment() {
    var tv: CardView?=null
    var click:Boolean=true;
    var adsdisposable: Disposable? = null
    var progressBar: LinearLayout? = null
    var ads_view: LinearLayout? = null
    lateinit var prefs: SharedPrefs
    var recycler_adds: RecyclerView? = null

    var adsList: ArrayList<Advertisement> = ArrayList()
    var adapter: AdvertismentAdapter? = null

    var id: Int? = null
    var token: String?=null

    lateinit var totaldirectcommission:TextView;
    lateinit var GetPayoutHistorySum:TextView;
    lateinit var GetEwalletCredit:TextView;
    lateinit var GetPaymentsInProcessSum:TextView;
    lateinit var GetEWalletSummarySponsorBonus:TextView;
    lateinit var GetEWalletDebitSum:TextView;
    lateinit var progressbar_dash:LinearLayout;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view  =inflater.inflate(R.layout.fragment_sleeping_dashboard, container, false)
        totaldirectcommission = view.findViewById(R.id.totaldirectcommission)
        GetEwalletCredit = view.findViewById(R.id.GetEwalletCredit)
        GetEWalletDebitSum = view.findViewById(R.id.GetEWalletDebitSum)
        GetPaymentsInProcessSum = view.findViewById(R.id.GetPaymentsInProcessSum)
        GetPayoutHistorySum = view.findViewById(R.id.GetPayoutHistorySum)
        GetEWalletSummarySponsorBonus = view.findViewById(R.id.GetEWalletSummarySponsorBonus)
        progressbar_dash = view.findViewById(R.id.progressbar_dash)

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
        adapter=AdvertismentAdapter(activity!!, "", adsList){ads->
            var img = Apputils.decodeFromBase64(ads.AdvertisementImage!!)
            if(img!=null)
            viewAdsDialog(img)

        }
        val manager = LinearLayoutManager(activity!!, LinearLayout.HORIZONTAL, false)
        recycler_adds!!.layoutManager = manager
        recycler_adds!!.adapter = adapter

        tv!!.setOnClickListener{
            if(click){
                if(GetEWalletSummarySponsorBonus.visibility!=View.VISIBLE)
                showBalanaceDialog()
            }
        }
        getads()
        getviewData()
        return view
    }

    private fun viewAdsDialog(bitmap: Bitmap) {
        val view: View = LayoutInflater.from(activity!!).inflate(R.layout.dialog_view_ads, null)
        val alertBox = android.support.v7.app.AlertDialog.Builder(activity!!)
        alertBox.setView(view)
        alertBox.setCancelable(true)
        val dialog = alertBox.create()
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        val img_ads: ImageView = view.findViewById(R.id.img_ads)
        val img_close: ImageView = view.findViewById(R.id.img_close)
        img_ads.setImageBitmap(bitmap)
        img_close.setOnClickListener{
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun showBalanaceDialog() {

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

    fun getviewData() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        ApiClint.getInstance()?.getService()?.getdashboardData("bearer " + token, id!!)
                ?.enqueue(object : Callback<DasboardData> {
                    override fun onFailure(call: Call<DasboardData>?, t: Throwable?) {
                        println("error")
                        progressbar_dash.visibility = View.GONE

                    }

                    override fun onResponse(call: Call<DasboardData>?, response: retrofit2.Response<DasboardData>?) {
                        print("object success ")
                        val code: Int = response!!.code()

                        if (code == 401) {
                            Apputils.showMsg(activity!!, "Token Expired")
                            //tokenExpire();

                        }
                        if (code == 200) {
                            print("success")
                            val obj: DasboardData = response.body()!!

                            if (obj.totaldirectcommission != null)
                                totaldirectcommission.text = obj.totaldirectcommission!!.split(".")[0]+" PKR";

                            if (obj.GetEwalletCredit != null)
                                GetEwalletCredit.text = obj.GetEwalletCredit!!.split(".")[0]+" PKR"

                            if (obj.GetEWalletDebitSum != null)
                                GetEWalletDebitSum.text = obj.GetEWalletDebitSum!!.split(".")[0]+" PKR"

                            if (obj.GetPaymentsInProcessSum != null)
                                GetPaymentsInProcessSum.text = obj.GetPaymentsInProcessSum!!.split(".")[0]+" PKR"


                            if (obj.GetPayoutHistorySum != null)
                                GetPayoutHistorySum.text = obj.GetPayoutHistorySum!!.split(".")[0]+" PKR"


                            if (obj.GetEWalletSummarySponsorBonus != null)
                                GetEWalletSummarySponsorBonus.text = obj.GetEWalletSummarySponsorBonus!!.split(".")[0]+" PKR"


                        }
                        progressbar_dash.visibility = View.GONE


                    }
                })
    }

    //<editor-fold desc="Advertisment control">
    private fun getads() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Apputils.showMsg(activity!!, "Network error")
            return
        }

        progressBar!!.visibility = View.VISIBLE
        ads_view!!.visibility = View.GONE
        val adsObserver = getadvertismentObserver()
        val adsObservable: Observable<ArrayList<Advertisement>> = MyApiRxClint.getInstance()?.getService()?.getCoinData()!!
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
                Apputils.showMsg(activity!!, "Network error")

            }

            override fun onNext(response: ArrayList<Advertisement>) {

                response.forEach { ads ->
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
