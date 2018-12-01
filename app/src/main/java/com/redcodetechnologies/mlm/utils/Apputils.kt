package com.redcodetechnologies.mlm.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.text.TextUtils
import android.widget.Toast
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.google.firebase.iid.FirebaseInstanceId
import com.redcodetechnologies.mlm.models.Response
import com.redcodetechnologies.mlm.models.profile.FcmModel
import com.redcodetechnologies.mlm.retrofit.ApiClint
import retrofit2.Call
import retrofit2.Callback
import java.io.ByteArrayOutputStream


class Apputils {
    companion object {
        fun showMsg(ctx: Activity, msg: String) {
            Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show()
        }


        fun isValidEmail(target: CharSequence): Boolean {
            return if (TextUtils.isEmpty(target)) {
                false
            } else {
                android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
            }
        }
        fun isNetworkAvailable(context: Context): Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var netInfo: NetworkInfo? = null
            if (cm != null) {
                netInfo= cm.activeNetworkInfo
            }
            return netInfo != null && netInfo.isConnectedOrConnecting
        }

        fun encodeToBase64(image: Bitmap): String {
            val byteArrayOS = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOS)
            return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT)
        }
        fun decodeFromBase64( img:String ): Bitmap {
            var imageBytes = Base64.decode(img, Base64.DEFAULT)
            val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            return decodedImage
        }

        fun updateFcm(ctx: Activity){
            val id = SharedPrefs.getInstance()!!.getUser(ctx).userId
            if(id==null)
                return

            if (!Apputils.isNetworkAvailable(ctx)) {
                return
            }

            val fcm = FirebaseInstanceId.getInstance().getToken()
            val obj = FcmModel(id,fcm!!)
            ApiClint.getInstance()?.getService()?.updateUserFcm(obj)
                    ?.enqueue(object : Callback<Response> {
                        override fun onFailure(call: Call<Response>?, t: Throwable?) {
                            println("error")
                        }

                        override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                            val msg = response!!.message()
                            print(msg)
                        }
                    })

        }
    }




}