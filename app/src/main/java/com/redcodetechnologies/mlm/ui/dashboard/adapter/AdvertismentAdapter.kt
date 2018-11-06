package com.redcodetechnologies.mlm.ui.dashboard.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Advertisement
import com.squareup.picasso.Picasso
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.InputStream


class AdvertismentAdapter(var ctx: Context, var type: String, var list: ArrayList<Advertisement>) : RecyclerView.Adapter<AdvertismentAdapter.MyViewHolder>() {

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

        fun bindView(addsmodal : Advertisement, typ :String) {
            tv_first_image = itemView.findViewById(R.id.first_image)
            if(addsmodal.AdvertisementImage!=null){
                //var stream :InputStream  =  ByteArrayInputStream(addsmodal.AdvertisementImage!!.getBytes(StandardCharsets.UTF_8))
            //val image = BitmapFactory.decodeStream(addsmodal.AdvertisementImage)
               var imageBytes = Base64.decode(addsmodal.AdvertisementImage!!, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

                tv_first_image!!.setImageBitmap(decodedImage)
            }
        }
    }
}
