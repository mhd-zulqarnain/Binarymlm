package com.redcodetechnologies.mlm.ui.dashboard

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.redcodetechnologies.mlm.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.widget.CardView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.widget.*
import com.redcodetechnologies.mlm.models.Advertisement
import com.redcodetechnologies.mlm.models.ApiToken
import com.redcodetechnologies.mlm.models.DasboardData
import com.redcodetechnologies.mlm.models.MakeTableData
import com.redcodetechnologies.mlm.models.users.NewUserRegistration
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.ui.auth.SignInActivity
import com.redcodetechnologies.mlm.ui.auth.UserCategoryActivity
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.ui.dashboard.adapter.AdvertismentAdapter
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.ServiceError
import com.redcodetechnologies.mlm.utils.ServiceListener
import com.redcodetechnologies.mlm.utils.SharedPrefs
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import io.reactivex.Observable;
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers;
import kotlinx.android.synthetic.main.activity_sign_in.*


@Suppress("DEPRECATION")
class DashBoardFragment : Fragment() {
    var frgement_type = "MyPackageCommisionList"
    var balance_card: CardView? = null
    var click: Boolean = true;
    lateinit var prefs: SharedPrefs

    var recycler_adds: RecyclerView? = null
    var tv_show_ads: TextView? = null
    var adsList: ArrayList<Advertisement> = ArrayList()
    var adapter: AdvertismentAdapter? = null
    var adsdisposable: Disposable? = null
    var progressbar_dash: LinearLayout? = null
    var progressbar_add: LinearLayout? = null

    var id: Int? = null
    lateinit var token: String

    //view
    var totaldirectcommission: TextView? = null
    var GetEwalletCredit: TextView? = null
    var GetEWalletDebitSum: TextView? = null
    var GetPaymentsInProcessSum: TextView? = null
    var GetUserTotalPackageCommission: TextView? = null
    var GetUserDownlineMembers: TextView? = null
    var GetPayoutHistorySum: TextView? = null
    var GetUserTotalMatchingCommission: TextView? = null
    var GetEWalletSummarySponsorBonus: TextView? = null
    var GetAllTotalLeftUserPV: TextView? = null
    var GetAllTotalRightUserPV: TextView? = null
    var GetTotalremainingleftamount: TextView? = null
    var GetTotalremainingrightamount: TextView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        prefs = SharedPrefs.getInstance()!!
        initView(view)
        getviewData()
        return view

    }

    private fun initView(view: View) {

        balance_card = view.findViewById(R.id.dashboardbalance) as CardView;


        recycler_adds = view.findViewById(R.id.recylcer_adds)
        tv_show_ads = view.findViewById(R.id.tv_show_ads)
        progressbar_dash = view.findViewById(R.id.progressbar_dash)
        progressbar_add = view.findViewById(R.id.progressbar_add)
        totaldirectcommission = view.findViewById(R.id.totaldirectcommission)
        GetEwalletCredit = view.findViewById(R.id.GetEwalletCredit)
        GetEWalletDebitSum = view.findViewById(R.id.GetEWalletDebitSum)
        GetPaymentsInProcessSum = view.findViewById(R.id.GetPaymentsInProcessSum)
        GetUserTotalPackageCommission = view.findViewById(R.id.GetUserTotalPackageCommission)
        GetUserDownlineMembers = view.findViewById(R.id.GetUserDownlineMembers)
        GetPayoutHistorySum = view.findViewById(R.id.GetPayoutHistorySum)
        GetUserTotalMatchingCommission = view.findViewById(R.id.GetUserTotalMatchingCommission)
        GetEWalletSummarySponsorBonus = view.findViewById(R.id.GetEWalletSummarySponsorBonus)
        GetAllTotalRightUserPV = view.findViewById(R.id.GetAllTotalRightUserPV)
        GetAllTotalLeftUserPV = view.findViewById(R.id.GetAllTotalLeftUserPV)
        GetTotalremainingleftamount = view.findViewById(R.id.GetTotalremainingleftamount)
        GetTotalremainingrightamount = view.findViewById(R.id.GetTotalremainingrightamount)

        val manager =  LinearLayoutManager((activity as Context?)!!, LinearLayout.HORIZONTAL, false)
        recycler_adds!!.layoutManager = manager
        adapter = AdvertismentAdapter(activity!!, frgement_type, adsList){ads->
            viewAdsDialog(Apputils.decodeFromBase64(ads.AdvertisementImage!!))
        }
        if (prefs.getUser(activity!!).userId != null) {
            id = prefs.getUser(activity!!).userId
            token = prefs.getToken(activity!!).accessToken!!
        }
        recycler_adds!!.adapter = adapter
        balance_card!!.setOnClickListener {
            if (click) {
                showBalanaceDialog()
            }
        }
       getads()
    }

    private fun showBalanaceDialog() {
        val view: View = LayoutInflater.from(activity!!).inflate(R.layout.dialogue_forget_password, null)
        val alertBox = android.support.v7.app.AlertDialog.Builder(activity!!)
        alertBox.setView(view)
        alertBox.setCancelable(true)
        val dialog = alertBox.create()
        dialog.window.setBackgroundDrawableResource(android.R.color.transparent);
        val tvtitle: TextView = view.findViewById(R.id.tvtitle)
        val tvdescription: TextView = view.findViewById(R.id.tvdescription)
        tvtitle.text = "Enter Password"
        tvdescription.layoutParams = LinearLayout.LayoutParams(10, 10, 1.75f)
        tvdescription.text = ""

        val ed_email_address: EditText = view.findViewById(R.id.ed_email)
        ed_email_address.visibility = View.GONE
        val pass: EditText = view.findViewById(R.id.ed_pass)
        pass.visibility = View.VISIBLE
        val button_submit: Button = view.findViewById(R.id.btn_submit)

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

    private fun getads() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Apputils.showMsg(activity!!, "Network error")
            return
        }

        progressbar_add!!.visibility = View.VISIBLE
        recycler_adds!!.visibility = View.GONE
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
                progressbar_add!!.visibility = View.GONE
                recycler_adds!!.visibility = View.VISIBLE


            }

            override fun onSubscribe(d: Disposable) {
                adsdisposable = d

            }

            override fun onError(e: Throwable) {
                progressbar_dash!!.visibility = View.VISIBLE
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

    fun getviewData() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        ApiClint.getInstance()?.getService()?.getdashboardData("bearer " + token, id!!)
                ?.enqueue(object : Callback<DasboardData> {
                    override fun onFailure(call: Call<DasboardData>?, t: Throwable?) {
                        println("error")
                        progressbar_dash!!.visibility = View.GONE

                    }

                    override fun onResponse(call: Call<DasboardData>?, response: retrofit2.Response<DasboardData>?) {
                        print("object success ")
                        val code: Int = response!!.code()

                        if (code == 401) {
                            Apputils.showMsg(activity!!, "Token Expired")
                            tokenExpire();

                        }
                        if (code == 200) {
                            print("success")
                            val obj: DasboardData = response.body()!!

                            if (obj.totaldirectcommission != null)
                                totaldirectcommission!!.text = obj.totaldirectcommission!!.split(".")[0]+" PKR";

                            if (obj.GetEwalletCredit != null)
                                GetEwalletCredit!!.text = obj.GetEwalletCredit!!.split(".")[0]+" PKR"

                            if (obj.GetEWalletDebitSum != null)
                                GetEWalletDebitSum!!.text = obj.GetEWalletDebitSum!!.split(".")[0]+" PKR"

                            if (obj.GetPaymentsInProcessSum != null)
                                GetPaymentsInProcessSum!!.text = obj.GetPaymentsInProcessSum!!.split(".")[0]+" PKR"
                            if (obj.GetUserTotalPackageCommission != null)
                                GetUserTotalPackageCommission!!.text = obj.GetUserTotalPackageCommission!!.split(".")[0]+" PKR"

                            if (obj.GetUserDownlineMembers != null)
                                GetUserDownlineMembers!!.text = obj.GetUserDownlineMembers!!.split(".")[0]

                            if (obj.GetPayoutHistorySum != null)
                                GetPayoutHistorySum!!.text = obj.GetPayoutHistorySum!!.split(".")[0]+" PKR"

                            if (obj.GetUserTotalMatchingCommission != null)
                                GetUserTotalMatchingCommission!!.text = obj.GetUserTotalMatchingCommission!!.split(".")[0]+" PKR"

                            if (obj.GetEWalletSummarySponsorBonus != null)
                                GetEWalletSummarySponsorBonus!!.text = obj.GetEWalletSummarySponsorBonus!!.split(".")[0]+" PKR"

                            if (obj.GetAllTotalLeftUserPV != null)
                                GetAllTotalLeftUserPV!!.text = obj.GetAllTotalLeftUserPV!!.split(".")[0]

                            if (obj.GetAllTotalRightUserPV != null)
                                GetAllTotalRightUserPV!!.text = obj.GetAllTotalRightUserPV!!.split(".")[0]

                            if (obj.GetTotalremainingleftamount != null)
                                GetTotalremainingleftamount!!.text = obj.GetTotalremainingleftamount!!.split(".")[0]+" PKR"

                            if (obj.GetTotalremainingrightamount != null)
                                GetTotalremainingrightamount!!.text = obj.GetTotalremainingrightamount!!.split(".")[0]+" PKR"


                        }
                        progressbar_dash!!.visibility = View.GONE


                    }
                })
    }

    fun tokenExpire() {
        prefs.clearToken(activity!!)
        prefs.clearUser(activity!!)
        startActivity(Intent(activity!!, SignInActivity::class.java))
        activity!!.finish()
    }


    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as DrawerActivity).getSupportActionBar()!!.setTitle("Dashboard")
        (activity as DrawerActivity).getSupportActionBar()?.setIcon(0)

    }

    override fun onDestroyView() {
        adsdisposable?.dispose()
        super.onDestroyView()
    }



}
