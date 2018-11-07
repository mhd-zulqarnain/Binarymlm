package com.redcodetechnologies.mlm.ui.support

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.ui.support.adapter.ReportAdapter
import com.redcodetechnologies.mlm.models.Report
import java.util.*
import android.text.Editable
import android.text.TextWatcher




class ReportFragment : Fragment() {
    var recylcer_down_member: RecyclerView? = null

    var report: ReportAdapter? =null

    val REQUSET_GALLERY_CODE: Int = 43
    var reportList: ArrayList<Report> = ArrayList()
    var dialog_title: TextView? = null
    var ed_name: EditText? = null
    var ed_uname: EditText? = null
    var ed_pass: EditText? = null
    var ed_phone: EditText? = null
    var ed_address: EditText? = null
    var ed_email: EditText? = null
    var ed_account: EditText? = null
    var ed_bank_name: EditText? = null
    var ed_cnic: EditText? = null
    var search_view: EditText? = null
    var dialog: AlertDialog? = null




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_report, container, false)
        recylcer_down_member = view.findViewById(R.id.recylcer_down_member)
        recylcer_down_member!!.layoutManager = LinearLayoutManager((activity as Context?)!!, LinearLayout.VERTICAL, false)

        reportList.add(Report("Ali","EasyPaisa","21212321321","Habib","12","0.5","12-10-2018","12-10-2018"))
        reportList.add(Report("Zulqarnain","EasyPaisa","132231231","Metro","16","1","10-8-2018","12-10-2018"))
        reportList.add(Report("Arif","JazzCash","546546545454","Alfalah","10","1.5","11-10-2018","12-10-2018"))



        report = ReportAdapter(activity!!, reportList) { post ->
            openreportdialog(reportList[post])
        }
        recylcer_down_member!!.adapter = report


        search_view  = view.findViewById(R.id.search_view)
        search_view!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })


        return view
    }

    private fun filter(text: String) {
        val filteredList = ArrayList<Report>()

        for (item in reportList) {
            if (item.UserName!!.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }

        report!!.filterList(filteredList)
    }
    private fun openreportdialog(report: Report) {
        val view: View = LayoutInflater.from((activity as Context?)!!).inflate(R.layout.report_dialog,null)
        val alertBox = android.support.v7.app.AlertDialog.Builder((activity as Context?)!!)
        alertBox.setView(view)
        dialog = alertBox.create()
        dialog!!.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            dialog!!.setCancelable(false);
       val  dialog_title :TextView = view.findViewById(R.id.dialog_title)

       val  et_rd_uname :TextView = view.findViewById(R.id.et_rd_uname)
       val  et_rd_pm :TextView = view.findViewById(R.id.et_rd_pm)
       val  et_rd_an :TextView = view.findViewById(R.id.et_rd_an)
       val  et_rd_bn :TextView = view.findViewById(R.id.et_rd_bn)
        val  et_rd_wc :TextView = view.findViewById(R.id.et_rd_wc)
        val  et_rd_ard :TextView = view.findViewById(R.id.et_rd_ard)
        val  et_rd_nap :TextView = view.findViewById(R.id.et_rd_nap)
        val  et_rd_pd :TextView = view.findViewById(R.id.et_rd_pd)
        val  btn_rd_ok :Button = view.findViewById(R.id.btn_rd_ok)

        dialog_title.text = arguments!!.getString("Fragment")


        et_rd_uname.text = et_rd_uname.text.toString() + report.UserName
        et_rd_pm.text = et_rd_pm.text.toString()+ report.PaymentMethod
        et_rd_an.text =  et_rd_an.text.toString()+ report.AccountNumber
        et_rd_bn.text =et_rd_bn.text.toString()+  report.BankName
        et_rd_wc.text = et_rd_wc.text.toString()+ report.WithdrawalCharges
        et_rd_ard.text = et_rd_ard.text.toString()+  report.ApprovedRequestDate
        et_rd_nap.text =et_rd_nap.text.toString()+  report.NetAmountPayble
        et_rd_pd.text =et_rd_pd.text.toString()+  report.PaidDate

        if(dialog_title.text!= "PayoutHistory")
            et_rd_pd.visibility=View.GONE

        btn_rd_ok.setOnClickListener{
            dialog!!.dismiss()
        }

        dialog!!.show()


    }


    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        (activity as DrawerActivity).getSupportActionBar()?.setTitle("Report")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUSET_GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
            println("data " + data.data)
        }
    }

}
