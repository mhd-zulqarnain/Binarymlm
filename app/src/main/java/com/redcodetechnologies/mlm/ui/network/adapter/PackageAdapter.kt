package com.redcodetechnologies.mlm.ui.network.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Packages
import com.redcodetechnologies.mlm.models.users.UserTree

class PackageAdapter(var ctx: Context, var list: ArrayList<Packages>) : RecyclerView.Adapter<PackageAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        val v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_package_item, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.bindView(list[p1])

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var pkg_name: TextView? = null
        var pkg_price: TextView? = null
        var pkg_approval: TextView? = null
        var pkg_date: TextView? = null


        fun bindView(pkg: Packages) {
            pkg_name = itemView.findViewById(R.id.pkg_name)
            pkg_price = itemView.findViewById(R.id.pkg_price)
            pkg_approval = itemView.findViewById(R.id.pkg_approval)
            pkg_date = itemView.findViewById(R.id.pkg_date)
            pkg_name!!.text = pkg.PackageName
            pkg_price!!.text = pkg.PackagePrice
            pkg_date!!.text = pkg.PurchaseDate!!.split("T")[0]

            var msg = "Approved"
            if (pkg.IsApprovedForBuy!!) msg = "Approved"
            if (pkg.IsRejectedForBuy!!) msg = "Rejected"
            if (pkg.IsRequestedForBuy!!) msg = "Requested"
            pkg_approval!!.text = msg

        }
    }
}
