package com.redcodetechnologies.mlm.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.redcodetechnologies.mlm.models.SentBox

class SentBoxAdopter (val items : ArrayList<SentBox>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        
    }

    override fun getItemCount(): Int {

                return items.size
        }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}