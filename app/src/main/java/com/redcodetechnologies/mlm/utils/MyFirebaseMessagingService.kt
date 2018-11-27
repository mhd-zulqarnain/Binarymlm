package com.redcodetechnologies.mlm.utils

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat


import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONException
import org.json.JSONObject
import android.annotation.SuppressLint

import android.app.NotificationChannel
import android.os.Build
import com.google.gson.Gson


class MyFirebaseMessagingService : FirebaseMessagingService() {
    var type = ""
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        if (remoteMessage!!.data.size > 0) {
            var messege = remoteMessage.data
            var json = Gson().toJson(remoteMessage.data)
            var body = JSONObject(json).get("body").toString()
            var data = body.split(",")
            var intent:Intent = Intent()

            var msg = ""


            if (data[0] == "release") {
                msg = "Order has been release"
            }
            if (data[0] == "order"){
                msg = "New Order placed"
            }
            if (data[0] == "cancel"){
                msg = "Order has been canceled"
            }
            if (data[0] == "paid"){
                msg = "Order has been paid"
            }
            if (data[0] == "dispute"){
                msg = "Order has been disputed"
            }
           /* intent = Intent(this@MyFirebaseMessagingService, OrderDetailActivity::class.java)
            intent.putExtra("type", "service")
            intent.putExtra("orderId", data[1])
            intent.putExtra("request", data[0])

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
            val channelId = "Default"
            val builder = NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher_logo)
                    .setContentTitle("RoyalCrypto")
                    .setContentText(msg).setAutoCancel(true).setContentIntent(pendingIntent)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT)
                manager.createNotificationChannel(channel)
            }*/
          //  manager.notify(0, builder.build())

        }
    }
}