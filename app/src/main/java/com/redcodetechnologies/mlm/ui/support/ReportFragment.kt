package com.redcodetechnologies.mlm.ui.support

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
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
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.redcodetechnologies.mlm.retrofit.MyApiRxClint
import com.redcodetechnologies.mlm.utils.Apputils
import com.redcodetechnologies.mlm.utils.SharedPrefs
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.FileOutputStream


class ReportFragment : Fragment() {
    var recylcer_down_member: RecyclerView? = null

    var adapter: ReportAdapter? = null

    val REQUSET_GALLERY_CODE: Int = 43
    var list: ArrayList<Report> = ArrayList()
    var dialog_title: TextView? = null
    var ed_name: EditText? = null
    var ed_uname: EditText? = null
    var ed_pass: EditText? = null
    var ed_phone: EditText? = null
    var ed_address: EditText? = null
    var ed_email: EditText? = null
    var search_view: EditText? = null
    lateinit var progressBar: LinearLayout
    lateinit var tv_no_data: LinearLayout
    lateinit var btn_gen_Pdf: Button

    var dialog: AlertDialog? = null
    var frgement_type = ""

    lateinit var prefs: SharedPrefs
    var id: Int? = null
    lateinit var token: String
    var disposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_report, container, false)
        recylcer_down_member = view.findViewById(R.id.recylcer_down_member)
        progressBar = view.findViewById(R.id.progressBar)
        tv_no_data = view.findViewById(R.id.tv_no_data)
        btn_gen_Pdf = view.findViewById(R.id.btn_gen_Pdf)
        recylcer_down_member!!.layoutManager = LinearLayoutManager((activity as Context?)!!, LinearLayout.VERTICAL, false)

        frgement_type = arguments?.getString("Fragment").toString();
        prefs = SharedPrefs.getInstance()!!
        if (prefs.getUser(activity!!).userId != null) {
            id = prefs.getUser(activity!!).userId
            token = prefs.getToken(activity!!).accessToken!!
        }

        /*   list.add(Report("asdfasd","asdfasd","asdfasd","asdfasd","asdfasd","asdfasd","asdfasd","asdfasd"))
           list.add(Report("asdfasd","asdfasd","asdfasd","asdfasd","asdfasd","asdfasd","asdfasd","asdfasd"))
           list.add(Report("asdfasd","asdfasd","asdfasd","asdfasd","asdfasd","asdfasd","asdfasd","asdfasd"))
         */  adapter = ReportAdapter(activity!!, list) { post ->
            openreportdialog(list[post])
        }
        recylcer_down_member!!.adapter = adapter


        search_view = view.findViewById(R.id.search_view)

        search_view!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })
        var title = ""
        if (frgement_type == "ActivePayout") {
            getactivepayout()
            title = "Active Payout"
        }
        if (frgement_type == "PayoutHistory") {
            getpayouthistory()

            title = "Payment History"

        }
        if (frgement_type == "PayoutWithdrawalinProcess") {
            getpayoutwithdrawinprocess()
            title = "Payment In Process"

        }

        (activity as DrawerActivity).getSupportActionBar()?.setTitle(title)
        (activity as DrawerActivity).getSupportActionBar()?.setIcon(0)


        btn_gen_Pdf.setOnClickListener {

            if (list.size!=0)
            generatePdf()
            else
                Apputils.showMsg(activity!!,"No Data to form the report")
        }
        return view
    }

    fun getactivepayout() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        if (!list.isEmpty())
            list.clear()
        progressBar!!.visibility = View.VISIBLE
        recylcer_down_member!!.visibility = View.GONE


        val observer = getObserver()
        val observable: Observable<ArrayList<Report>> = MyApiRxClint.getInstance()!!.getService()!!.getactivepayout(id!!)
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)

    }

    fun getpayouthistory() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        if (!list.isEmpty())
            list.clear()
        progressBar!!.visibility = View.VISIBLE
        recylcer_down_member!!.visibility = View.GONE

        val observer = getObserver()
        val observable: Observable<ArrayList<Report>> = MyApiRxClint.getInstance()!!.getService()!!.getpayouthistory(id!!)
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)

    }

    fun getpayoutwithdrawinprocess() {

        if (!Apputils.isNetworkAvailable(activity!!)) {
            Toast.makeText(activity!!, " Network error ", Toast.LENGTH_SHORT).show()
            return
        }
        if (!list.isEmpty())
            list.clear()
        progressBar!!.visibility = View.VISIBLE


        val observer = getObserver()
        val observable: Observable<ArrayList<Report>> = MyApiRxClint.getInstance()!!.getService()!!.getpayoutwithdrawinprocess(id!!)
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer)

    }

    fun getObserver(): Observer<ArrayList<Report>> {

        return object : Observer<ArrayList<Report>> {
            override fun onComplete() {
                progressBar.visibility = View.GONE


            }

            override fun onSubscribe(d: Disposable) {
                disposable = d
            }

            override fun onNext(t: ArrayList<Report>) {

                t.forEach { obj ->
                    list.add(obj)

                }
                adapter!!.notifyDataSetChanged()

                if (t.size == 0) {
                    recylcer_down_member!!.visibility = View.GONE
                    tv_no_data.visibility = View.VISIBLE
                } else {
                    tv_no_data.visibility = View.GONE
                    recylcer_down_member!!.visibility = View.VISIBLE
                }
            }

            override fun onError(e: Throwable) {
                print("error")
            }
        }
    }

    private fun filter(text: String) {
        val filteredList = ArrayList<Report>()

        for (item in list) {
            if (item.Username!!.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
            }
        }

        adapter!!.filterList(filteredList)
    }

    private fun openreportdialog(report: Report) {
        val view: View = LayoutInflater.from((activity as Context?)!!).inflate(R.layout.report_dialog, null)
        val alertBox = android.support.v7.app.AlertDialog.Builder((activity as Context?)!!)
        alertBox.setView(view)
        dialog = alertBox.create()
        dialog!!.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        dialog!!.setCancelable(false);
        val dialog_title: TextView = view.findViewById(R.id.dialog_title)

        val et_rd_uname: TextView = view.findViewById(R.id.et_rd_uname)
        val et_rd_pm: TextView = view.findViewById(R.id.et_rd_pm)
        val et_rd_an: TextView = view.findViewById(R.id.et_rd_an)
        val et_rd_bn: TextView = view.findViewById(R.id.et_rd_bn)
        val et_rd_wc: TextView = view.findViewById(R.id.et_rd_wc)
        val et_rd_ard: TextView = view.findViewById(R.id.et_rd_ard)
        val et_rd_nap: TextView = view.findViewById(R.id.et_rd_nap)
        val et_rd_pd: TextView = view.findViewById(R.id.et_rd_pd)
        val btn_rd_ok: Button = view.findViewById(R.id.btn_rd_ok)

        dialog_title.text = arguments!!.getString("Fragment")


        et_rd_uname.text = et_rd_uname.text.toString() + report.Username
        et_rd_an.text = et_rd_an.text.toString() + report.AccountNumber
        et_rd_bn.text = et_rd_bn.text.toString() + report.BankName
        et_rd_wc.text = et_rd_wc.text.toString() + report.WithdrawalFundCharge
        et_rd_ard.text = et_rd_ard.text.toString() + report.ApprovedDate
        et_rd_nap.text = et_rd_nap.text.toString() + report.AmountPayble
        et_rd_pd.text = et_rd_pd.text.toString() + report.PaidDate
        et_rd_pm.text = et_rd_pm.text.toString() + report.WithdrawalFundMethod

        if (dialog_title.text != "PayoutHistory")
            et_rd_pd.visibility = View.GONE

        btn_rd_ok.setOnClickListener {
            dialog!!.dismiss()
        }

        dialog!!.show()


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUSET_GALLERY_CODE && resultCode == Activity.RESULT_OK && data != null) {
            println("data " + data.data)
        }
    }

    override fun onDestroyView() {
        if (disposable != null)
            disposable!!.dispose()
        super.onDestroyView()
    }

    fun generatePdf() {
        val doc = Document()
        val random = Random().nextInt(100)
        val filepath = Environment.getExternalStorageDirectory().path + "/report$random.pdf"
        PdfWriter.getInstance(doc, FileOutputStream(filepath))
        doc.open()

        val table = PdfPTable(7);

        table.setWidthPercentage(100F);
        table.setSpacingBefore(0f);
        table.setSpacingAfter(0f);

        val cell = PdfPCell()
        cell.setColspan(7);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(5.0f);
        table.addCell(cell);

        table.addCell("User Name ");
        table.addCell("Payment Method");
        table.addCell("Account Number");
        table.addCell("Bank Name");
        table.addCell("Net Amount Payble");
        table.addCell("Withdrawal Charges");
        table.addCell("Approved Request Date");

        list.forEach { obj ->
            table.addCell(obj.Username);
            table.addCell(obj.WithdrawalFundMethod);
            table.addCell(obj.AccountNumber);
            table.addCell(obj.BankName);
            table.addCell(obj.AmountPayble);
            table.addCell(obj.WithdrawalFundCharge);
            table.addCell(obj.ApprovedDate)
        }

        doc.add(table)
        doc.close()
        print("generated $filepath")
        Apputils.showMsg(activity!!,"Report generated successfully")

    }

}
