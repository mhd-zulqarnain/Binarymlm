package com.redcodetechnologies.mlm.ui.wallet

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.iid.FirebaseInstanceId
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Response
import com.redcodetechnologies.mlm.models.Withdrawfund
import com.redcodetechnologies.mlm.models.profile.FcmModel
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.SharedPrefs
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback

class WithdrawalFundFragment : Fragment() {

    lateinit var tv_current_pkg_amount:TextView
    lateinit var tv_pkg_amount_limit:TextView
    lateinit var tv_amount_already_payout:TextView
    lateinit var tv_processing_charges:TextView
    lateinit var tv_min_withdraw:TextView
    lateinit var tv_max_withdraw:TextView
    lateinit var tv_prefered_pay_method:TextView
    lateinit var progressdialog: android.app.AlertDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_withdrawalfund, container, false)
        (activity as DrawerActivity).getSupportActionBar()?.setTitle("Wallet Withdrawal Fund")
        (activity as DrawerActivity).getSupportActionBar()?.setIcon(0)

        progressdialog = SpotsDialog.Builder()
                .setContext(activity!!)
                .setMessage("Loading!!")
                .setTheme(R.style.CustomProgess)
                .build()
        initView(view)

        return view

    }

    private fun initView(view: View) {

        tv_current_pkg_amount = view.findViewById(R.id.tv_current_pkg_amount)
        tv_pkg_amount_limit = view.findViewById(R.id.tv_pkg_amount_limit)
        tv_amount_already_payout = view.findViewById(R.id.tv_amount_already_payout)
        tv_processing_charges = view.findViewById(R.id.tv_processing_charges)
        tv_min_withdraw = view.findViewById(R.id.tv_min_withdraw)
        tv_prefered_pay_method = view.findViewById(R.id.tv_prefered_pay_method)

        getalluserwithdrawfundDetails()

    }

    fun getalluserwithdrawfundDetails(){


        val user = SharedPrefs.getInstance()!!.getUser(activity!!)
        val id = user.userId
        tv_prefered_pay_method.setText(user.bankName)

        if(id==null)
            return

        if (!Apputils.isNetworkAvailable(activity!!)) {
            return
        }
        progressdialog.show()
        ApiClint.getInstance()?.getService()?.getalluserwithdrawfund(id!!)
                ?.enqueue(object : Callback<Withdrawfund> {
                    override fun onFailure(call: Call<Withdrawfund>?, t: Throwable?) {
                        println("error")
                        progressdialog.dismiss();

                    }

                    override fun onResponse(call: Call<Withdrawfund>?, response: retrofit2.Response<Withdrawfund>?) {
                        val msg = response!!.message()
                        val code:Int = response.code()

                        if(code==200){
                            var obj :Withdrawfund = response.body()!!;

                            if(obj.GetUserPackageCommissionAmount!="")
                            tv_current_pkg_amount.text= obj.GetUserPackageCommissionAmount.split(".")[0]+"PKR"

                            if(obj.GetUserPackageAmountLimitForWithdrawal!="")
                                tv_pkg_amount_limit.text= obj.GetUserPackageAmountLimitForWithdrawal.split(".")[0]+"PKR"

                            /*if(obj.GetUserEWalletAmountInProcess!=null)
                                tv_amount_already_payout.text= obj.MinimumPayout*/

                            if(obj.PayoutChargesPercent!="")
                                tv_processing_charges.text= obj.PayoutChargesPercent+"%"

                            if(obj.PayoutChargesPercent!="")
                                tv_min_withdraw.text= obj.MinimumPayout.split(".")[0]+"PKR"
                        }
                        print(msg)
                        progressdialog.dismiss();

                    }
                })

    }


}
