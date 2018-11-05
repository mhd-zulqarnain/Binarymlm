package com.redcodetechnologies.mlm.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.text.TextUtils
import android.widget.Toast

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
    }


}