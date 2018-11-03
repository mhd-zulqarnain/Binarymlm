package com.redcodetechnologies.mlm.ui.support.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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


        var et_rd_uname: TextView? = null
        var et_rd_pm: TextView? = null
        var et_rd_an: TextView? = null
        var et_rd_bn: TextView? = null

        var btn_rd_view: Button? = null

        fun bindView(users: Report) {



            et_rd_uname = itemView.findViewById(R.id.et_rd_uname)
            et_rd_pm = itemView.findViewById(R.id.et_rd_pm)
            et_rd_an = itemView.findViewById(R.id.et_rd_an)
            et_rd_bn = itemView.findViewById(R.id.et_rd_bn)
       //     et_rd_bn!!.layoutParams = LinearLayout.LayoutParams(30, 30, 0f)
            btn_rd_view = itemView.findViewById(R.id.btn_rd_view)





            et_rd_uname!!.text = users.UserName
            et_rd_pm!!.text = users.PaymentMethod
            var an :String = users.AccountNumber!!.substring(0,7) +".."
            et_rd_an!!.text = an
            et_rd_bn!!.text = users.BankName




            //  tv_price!!.text = order.BitPrice

        }
    }

    fun filterList(filteredList: ArrayList<Report>) {
        datalist = filteredList
        notifyDataSetChanged()
    }
}


