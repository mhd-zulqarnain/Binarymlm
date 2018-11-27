package com.redcodetechnologies.mlm.ui.geologytable

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.wallet.TransactionModal


class PackageCommisionListAdapter(var ctx: Context, var type: String, var list: ArrayList<TransactionModal>, val onClick:(TransactionModal)->Unit) : RecyclerView.Adapter<PackageCommisionListAdapter.MyViewHolder>() {

    var typ=type


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_package_list_commision, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.bindView(list[p1],typ)
        p0.btn_ok!!.setOnClickListener{
            //action perform
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_source: TextView? = null
        var tv_name: TextView? = null
        var tv_amount: TextView? = null
        var tv_date: TextView? = null
        var btn_ok: Button? = null
        fun bindView(packagecommisionlist : TransactionModal, typ :String) {

            tv_source = itemView.findViewById(R.id.tran_source)
            tv_name = itemView.findViewById(R.id.tran_name)
            tv_amount = itemView.findViewById(R.id.amount)
            tv_date = itemView.findViewById(R.id.tran_date)
            btn_ok = itemView.findViewById(R.id.btn_ok)

            if(typ=="MyPackageCommisionList") {
                btn_ok!!.visibility = View.GONE
                tv_source!!.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.75f)
                tv_name!!.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.75f)
                tv_amount!!.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.75f)
                tv_date!!.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.75f)
                btn_ok!!.layoutParams = LinearLayout.LayoutParams(0, 0, 0f)
            }
                tv_source!!.text = packagecommisionlist.TransactionSource
                tv_name!!.text = packagecommisionlist.TransactionName
                tv_amount!!.text = packagecommisionlist.Amount
                tv_date!!.text = packagecommisionlist.TransactionDate


            //  tv_price!!.text = order.BitPrice!

        }
    }
}
