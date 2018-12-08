package com.redcodetechnologies.mlm.ui.videos.vediopack

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.PackVideo


class VideoDetailAdapter(var ctx: Context, var list: ArrayList<PackVideo>, private val onClick:(PackVideo)->Unit) : RecyclerView.Adapter<VideoDetailAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        val v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_videos_pack, parent, false))
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
        var image_thumbnail: ImageView? = null
        fun bindView(video: PackVideo) {
            video_title_descripton = itemView.findViewById(R.id.video_title_descripton)
            video_title_label = itemView.findViewById(R.id.video_title_label)
            image_thumbnail = itemView.findViewById(R.id.image_thumbnail)
            video_title_descripton!!.setText(video.VideoPackName)
            video_title_label!!.setText(video.VideoPackDesc)

            if(video.VideoPackImage!=null){
                image_thumbnail!!.setImageBitmap(stringtoImage(video.VideoPackImage!!))
            }
        }
        fun stringtoImage(encodedString: String): Bitmap? {
            try {
                val encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
                val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size);
                return bitmap;

            } catch (e: Exception) {
                return null
            }
        }


    }

}