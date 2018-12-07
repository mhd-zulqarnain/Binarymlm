package com.redcodetechnologies.mlm.ui.videos.vediopack

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.PackVideo
import com.redcodetechnologies.mlm.models.VedioCategory

class VideoCategoryAdapter(var ctx: Context, var list: ArrayList<VedioCategory>, private val onClick:(VedioCategory)->Unit) : RecyclerView.Adapter<VideoCategoryAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_videos_pack, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, p1: Int) {
        holder.bindView(list[p1])
        holder.itemView.setOnClickListener {
            onClick(list[p1])
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var video_title_label: TextView? = null
        var video_title_descripton: TextView? = null

        fun bindView(video: VedioCategory) {
            video_title_descripton = itemView.findViewById(R.id.video_title_descripton)
            video_title_label = itemView.findViewById(R.id.video_title_label)

            video_title_descripton!!.setText(video.Category_Desc)
            video_title_label!!.setText(video.Category_Name)
        }

    }

}