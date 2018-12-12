package com.redcodetechnologies.mlm.ui.wallet

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.users.Users
import com.redcodetechnologies.mlm.models.wallet.WalletSummery
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.SharedPrefs
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EWalletSummaryFragment() : Fragment() {
    var progressdialog: android.app.AlertDialog? = null

    lateinit var tv_bonus_summery_balance: TextView
    lateinit var tv_bonus_summery_bonus: TextView

    lateinit var tv_bonus_ty_balance: TextView
    lateinit var tv_bonus_ty_bonus: TextView

    lateinit var tv_bonus_tm_bonus: TextView
    lateinit var tv_bonus_tm_balance: TextView


    lateinit var prefs: SharedPrefs
    var id: String? = null
    lateinit var token: String
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_ewalletsummary, container, false)
        (activity as DrawerActivity).getSupportActionBar()?.setTitle("Wallet Summary")
        prefs = SharedPrefs.getInstance()!!

        if (prefs.getUser(activity!!).userId != null) {
            id = prefs.getUser(activity!!).userId.toString()
            token = prefs.getToken(activity!!).accessToken!!
        }
        initView(view)

        return view
    }

    private fun initView(view: View) {
        progressdialog = SpotsDialog.Builder()
                .setContext(activity!!)
                .setMessage("Loading!!")
                .setTheme(R.style.CustomProgess)
                .build()

        tv_bonus_summery_balance = view.findViewById(R.id.tv_bonus_summery_balance)
        tv_bonus_summery_bonus = view.findViewById(R.id.tv_bonus_summery_bonus)
        tv_bonus_ty_balance = view.findViewById(R.id.tv_bonus_ty_balance)
        tv_bonus_ty_bonus = view.findViewById(R.id.tv_bonus_ty_bonus)
        tv_bonus_tm_bonus = view.findViewById(R.id.tv_bonus_tm_bonus)
        tv_bonus_tm_balance = view.findViewById(R.id.tv_bonus_tm_balance)
        getSummery()
        getSummerythisYear()
        getSummerythisYear()
        getSummerythismonth()
    }

    fun getSummery() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }

        progressdialog!!.show()
        ApiClint.getInstance()?.getService()?.getSummery(id!!)
                ?.enqueue(object : Callback<WalletSummery> {
                    override fun onFailure(call: Call<WalletSummery>?, t: Throwable?) {
                        print("error")
                        progressdialog!!.dismiss()
                    }

                    override fun onResponse(call: Call<WalletSummery>?, response: Response<WalletSummery>?) {
                        print("object success ")
                        var code: Int = response!!.code()

                        if (code == 200 && response.body() != null) {
                            var obj: WalletSummery = response.body()!!

                            tv_bonus_summery_bonus.setText(obj.bonus.split(".")[0]+" PKR")
                            tv_bonus_summery_balance.setText(obj.witdraw.split(".")[0]+" PKR")
                        }
                    }
                })

    }

    fun getSummerythisYear() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }

        ApiClint.getInstance()?.getService()?.getSummerythisYear(id!!)
                ?.enqueue(object : Callback<WalletSummery> {
                    override fun onFailure(call: Call<WalletSummery>?, t: Throwable?) {
                        print("error")
                        progressdialog!!.dismiss()

                    }

                    override fun onResponse(call: Call<WalletSummery>?, response: Response<WalletSummery>?) {
                        print("object success ")
                        var code: Int = response!!.code()

                        if (code == 200 && response.body() != null) {
                            var obj: WalletSummery = response.body()!!

                            tv_bonus_ty_bonus.setText(obj.bonus.split(".")[0]+" PKR")
                            tv_bonus_ty_balance.setText(obj.witdraw.split(".")[0]+" PKR")
                        }
                    }
                })

    }

    fun getSummerythismonth() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }

        ApiClint.getInstance()?.getService()?.getSummerythismonth(id!!)
                ?.enqueue(object : Callback<WalletSummery> {
                    override fun onFailure(call: Call<WalletSummery>?, t: Throwable?) {
                        print("error")
                        progressdialog!!.dismiss()
                    }

                    override fun onResponse(call: Call<WalletSummery>?, response: Response<WalletSummery>?) {
                        print("object success ")
                        var code: Int = response!!.code()

                        if (code == 200 && response.body() != null) {
                            var obj: WalletSummery = response.body()!!

                            tv_bonus_tm_bonus.setText(obj.bonus.split(".")[0]+" PKR")
                            tv_bonus_tm_balance.setText(obj.witdraw.split(".")[0]+" PKR")
                        }
                        progressdialog!!.dismiss()

                    }
                })

    }
}


