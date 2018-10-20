package com.company.redcode.royalcryptoexchange.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.text.TextUtils
import android.widget.Toast
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.Arrays.asList


class Apputils {
    companion object {
        fun showMsg(ctx: Activity, msg: String) {
            Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show()
        }

        fun formatCurrency(amount: String): String {
            val formatter = DecimalFormat("###,###,##")
            return formatter.format(java.lang.Double.parseDouble(amount))
        }

        fun isValidEmail(target: CharSequence): Boolean {
            return if (TextUtils.isEmpty(target)) {
                false
            } else {
                android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
            }
        }

        fun getTimeStamp(time: String): String? {
            try {
                val dateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa")
                // dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5"));
                val parsedDate = dateFormat.parse(time)
                val time = parsedDate.getTime()
                return time.toString()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return null
        }

        fun stringClean(inputStr: String): String {
//            val items = arrayOf("Hello", "World")
//            val input = "Hello world my # is 123 mail me @ test@test.com  03368625703"
            val EMAIL_PATTERN = "([^.@\\s]+)(\\.[^.@\\s]+)*@([^.@\\s]+\\.)+([^.@\\s]+)"

            val output = inputStr.replace(EMAIL_PATTERN.toRegex(), "") // Replace emails by an empty string

                    .replace("(?:[0-9] ?){11,11}".toRegex(), "") // Replace any digit by an empty string

           /* for (i in items.indices) {
                if (output.contains(items[i])) {
                    return true
                }
            }*/
            return output
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