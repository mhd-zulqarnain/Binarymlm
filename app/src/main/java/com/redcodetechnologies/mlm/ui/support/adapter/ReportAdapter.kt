package com.redcodetechnologies.mlm.ui.support.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Report
import com.redcodetechnologies.mlm.utils.CustomNameSearch

class ReportAdapter(var ctx: Context, var datalist: ArrayList<Report>, private val onItemClick: (Int) -> Unit): RecyclerView.Adapter<ReportAdapter.MyViewHolder>() {
    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.bindView(datalist[p1])
        p0.btn_rd_view!!.setOnClickListener{
        onItemClick(p1)
        }
    }

    var customFilter: CustomNameSearch? = null

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_row_report, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return datalist.size
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_uname: TextView? = null
        var tv_payment_method: TextView? = null
        var tv_price: TextView? = null
        var tv_status: TextView? = null
        var btn_rd_view: LinearLayout? = null
        fun bindView(report: Report) {

            tv_uname = itemView.findViewById(R.id.tv_uname)
            tv_payment_method = itemView.findViewById(R.id.tv_payment_method)
            tv_price = itemView.findViewById(R.id.tv_price)
            tv_status = itemView.findViewById(R.id.tv_status)
            btn_rd_view = itemView.findViewById(R.id.btn_rd_view)

            tv_uname!!.text = report.Username
            tv_payment_method!!.text = report.BankName
            var an :String = report.AccountNumber!!.substring(0,7) +".."
            tv_payment_method!!.text = an
            tv_status!!.text = report.BankName
   }
    }

    fun filterList(filteredList: ArrayList<Report>) {
        datalist = filteredList
        notifyDataSetChanged()
    }
}


