package com.redcodetechnologies.mlm.ui.wallet

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.iid.FirebaseInstanceId
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Response
import com.redcodetechnologies.mlm.models.Withdrawfund
import com.redcodetechnologies.mlm.models.profile.FcmModel
import com.redcodetechnologies.mlm.models.wallet.EWalletWithdrawalFundModel
import com.redcodetechnologies.mlm.retrofit.ApiClint
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.ServiceError
import com.redcodetechnologies.mlm.utils.ServiceListener
import com.redcodetechnologies.mlm.utils.SharedPrefs
import dmax.dialog.SpotsDialog
import retrofit2.Call
import retrofit2.Callback

class WithdrawalFundFragment : Fragment() {

    lateinit var tv_current_pkg: TextView
    lateinit var tv_total_pkg_commision: TextView
    lateinit var tv_amount_already_payout: TextView
    lateinit var tv_processing_charges: TextView
    lateinit var tv_pkg_amount_withdrawal: TextView
    lateinit var tv_min_withdraw: TextView
    lateinit var tv_max_withdraw: TextView
    lateinit var tv_prefered_pay_method: TextView
    lateinit var progressdialog: android.app.AlertDialog
    lateinit var btn_submit_request: Button
    lateinit var ed_amount_withdrawn: EditText

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

        tv_current_pkg = view.findViewById(R.id.tv_current_pkg)
        tv_total_pkg_commision = view.findViewById(R.id.tv_total_pkg_commision)
        tv_pkg_amount_withdrawal = view.findViewById(R.id.tv_pkg_amount_withdrawal)
        tv_amount_already_payout = view.findViewById(R.id.tv_amount_already_payout)
        tv_processing_charges = view.findViewById(R.id.tv_processing_charges)
        tv_min_withdraw = view.findViewById(R.id.tv_min_withdraw)
        tv_prefered_pay_method = view.findViewById(R.id.tv_prefered_pay_method)
        btn_submit_request = view.findViewById(R.id.btn_submit_request)
        ed_amount_withdrawn = view.findViewById(R.id.ed_amount_withdrawn)

        getalluserwithdrawfundDetails()
        btn_submit_request.isEnabled = false


        btn_submit_request.setOnClickListener {
            var packageCurrentAmount = tv_total_pkg_commision.text.toString().toInt()
            var packageAmountForWithdrawal = tv_pkg_amount_withdrawal.text.toString().toInt()
            if (packageCurrentAmount >= packageAmountForWithdrawal && ed_amount_withdrawn.text.toString().trim()!="") {
                submitWithDrawalRequest(object : ServiceListener<String> {
                    override fun success(obj: String) {
                        Apputils.showMsg(activity!!, msg = obj)
                    }

                    override fun fail(error: ServiceError) {}
                })
            }else{
                Apputils.showMsg(activity!!, "Warning!your amount is less then withdrawal limit")

            }
        }
    }

    fun submitWithDrawalRequest(serviceListener: ServiceListener<String>) {

        val user = SharedPrefs.getInstance()!!.getUser(activity!!)
        val id = user.userId
        tv_prefered_pay_method.setText(user.bankName)

        if (id == null)
            return

        if (!Apputils.isNetworkAvailable(activity!!)) {
            serviceListener.success("Network error")
            return
        }

        val obj = EWalletWithdrawalFundModel()
        obj.AmountPayble = ed_amount_withdrawn.text.toString()

        progressdialog.show()
        ApiClint.getInstance()?.getService()?.submitwithdrawalfundRequest(id, obj)
                ?.enqueue(object : Callback<Response> {
                    override fun onFailure(call: Call<Response>?, t: Throwable?) {
                        println("error")
                        progressdialog.dismiss();
                    }

                    override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {

                        val res: Response = response!!.body()!!
                        serviceListener.success(res.message!!)
                        progressdialog.dismiss();

                    }
                })
    }

    fun getalluserwithdrawfundDetails() {


        val user = SharedPrefs.getInstance()!!.getUser(activity!!)
        val id = user.userId
        tv_prefered_pay_method.setText(user.bankName)

        if (id == null)
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
                        val code: Int = response.code()

                        if (code == 200) {
                            var obj: Withdrawfund = response.body()!!;

                            if (obj.GetUserPackageCommissionAmount != "")
                                tv_total_pkg_commision.text = obj.GetUserPackageCommissionAmount.split(".")[0]

                            if (obj.PackageName != "")
                                tv_current_pkg.text = obj.PackageName

                            if (obj.GetUserPackageAmountLimitForWithdrawal != "")
                                tv_pkg_amount_withdrawal.text = obj.GetUserPackageAmountLimitForWithdrawal.split(".")[0]

                            if (obj.GetUserEWalletAmountInProcess != "")
                                tv_amount_already_payout.text = obj.GetUserEWalletAmountInProcess + "%"

                            if (obj.PayoutChargesPercent != "")
                                tv_processing_charges.text = obj.PayoutChargesPercent.split(".")[0] + "PKR"

                            if (obj.MinimumPayout != "")
                                tv_min_withdraw.text = obj.MinimumPayout.split(".")[0] + "PKR"
                        }
                        print(msg)
                        btn_submit_request.isEnabled = true

                        progressdialog.dismiss();

                    }
                })
    }


}
