package com.redcodetechnologies.mlm.ui.videos

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.TextView
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubeThumbnailLoader
import com.google.android.youtube.player.YouTubeThumbnailView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.VideosModal
import com.redcodetechnologies.mlm.models.users.Users
import com.redcodetechnologies.mlm.ui.network.adapter.DownMemberAdapter
import com.redcodetechnologies.mlm.utils.Constants

class VideosAdapter(var ctx: Context, var list: ArrayList<VideosModal>, private val onClick:(VideosModal)->Unit) : RecyclerView.Adapter<VideosAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MyViewHolder {
        var v = MyViewHolder(LayoutInflater.from(ctx).inflate(R.layout.single_videos_list, parent, false))
        return v
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: MyViewHolder, p1: Int) {
        holder.bindView(list[p1])
        holder.itemView.setOnClickListener {
            onClick(list[p1])
        }
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var videoThumbnailImageView: YouTubeThumbnailView
        var videoTitle:TextView

        fun bindView(users: VideosModal , typ :String) {
            videoThumbnailImageView = itemView.findViewById(R.id.video_thumbnail_image_view)
            videoTitle = itemView.findViewById(R.id.video_title_label)
        }

        fun bindView(users: Users , typ :String) {


//            itemView.setClickable(true);

            tv_name = itemView.findViewById(R.id.tv_name)
            tv_phone = itemView.findViewById(R.id.tv_phone)
            card_members = itemView.findViewById(R.id.card_members)
            tv_bank = itemView.findViewById(R.id.tv_bank)
            tv_sponser = itemView.findViewById(R.id.tv_sponser)
            tv_paid = itemView.findViewById(R.id.tv_paid)



            if(users.Username!=null)
                tv_name!!.text = users.Username
            if(users.Phone!=null)
                tv_phone!!.text = users.Phone
            if(users.BankName!=null)
                tv_bank!!.text = users.BankName!!
            if(users.SponsorName!=null)
                tv_sponser!!.text = users.SponsorName
            if(users.PaidAmount!=null)
                tv_paid!!.text = users.PaidAmount!!.split(".")[0]
        }
}