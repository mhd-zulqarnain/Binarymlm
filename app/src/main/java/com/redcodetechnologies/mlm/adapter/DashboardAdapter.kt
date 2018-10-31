package com.redcodetechnologies.mlm.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.AddsModal
import com.squareup.picasso.Picasso

class DashboardAdapter(var ctx: Context, var type: String, var list: ArrayList<AddsModal>) : RecyclerView.Adapter<DashboardAdapter.MyViewHolder>() {

    var typ=type


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_row_dashboard, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        p0.bindView(list[p1],typ)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_first_image: ImageView? = null
        fun bindView(addsmodal : AddsModal, typ :String) {
            tv_first_image = itemView.findViewById(R.id.first_image)
            Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(tv_first_image);
            //tv_first_image!!.text = addsmodal.adds_url


        }
    }
}
