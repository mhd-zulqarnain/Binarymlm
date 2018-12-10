package com.redcodetechnologies.mlm.utils


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

import com.google.gson.Gson
import com.redcodetechnologies.mlm.R
import com.redcodetechnologies.mlm.models.Messages
import com.redcodetechnologies.mlm.models.MyNotification
import com.redcodetechnologies.mlm.ui.auth.SignInActivity
import com.redcodetechnologies.mlm.ui.auth.UserCategoryActivity
import com.redcodetechnologies.mlm.ui.drawer.DrawerActivity


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {

        if (remoteMessage!!.data.size > 0) {

            /*    val json = Gson().toJson(remoteMessage.data)
                val body = JSONObject(json).get("body").toString()*/

            val data = remoteMessage.data
            if (data.get("type") == "notification")
                notification(data)
            else if (data.get("type") == "message")
                msgNotification(data)


        }
    }

    fun notification(data: MutableMap<String, String>) {
        val notification = MyNotification()
        notification.NotificationDescription = data.get("descrption")
        notification.NotificationId = data.get("id")
        notification.UserId = data.get("uid")
        notification.NotificationName = data.get("name")
        notification.IsSaveByUser = data.get("issaved")

        var intent = Intent()
        val user = SharedPrefs.getInstance()!!.getUser(this@MyFirebaseMessagingService)

        if (user.userId != null) {

            if (!user.isSalesExecutive!! && !user.isSleepingPartner!!) {
                intent = Intent(this@MyFirebaseMessagingService, UserCategoryActivity::class.java)
            } else
                when (user.isSalesExecutive) {
                    true -> {
                        intent = Intent(this@MyFirebaseMessagingService, DrawerActivity::class.java)
                        intent.putExtra("Category", "Sales")
                    }
                    false -> {
                        intent = Intent(this@MyFirebaseMessagingService, DrawerActivity::class.java)
                        intent.putExtra("Category", "Sleeping")
                    }
                }
        } else {
            intent = Intent(this@MyFirebaseMessagingService, SignInActivity::class.java)
            intent.putExtra("Category", "Sales")
        }

        val obj = Gson().toJson(notification)
        intent.putExtra("notification", obj)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = "Default"
        val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.sleepinglogo)
                .setContentTitle(notification.NotificationName)
                .setColor(getResources().getColor(R.color.colorWhite))
                .setContentText(notification.NotificationDescription).setAutoCancel(true).setContentIntent(pendingIntent)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        manager.notify(0, builder.build())
    }

    fun msgNotification(data: MutableMap<String, String>) {
        val notification = Messages()
        notification.Sender_Name = data.get("sname")
        notification.UserId = data.get("uid")!!.toInt()
        notification.SponserId = data.get("sid")!!.toInt()
        notification.Message = data.get("message")

        var intent = Intent()

        val user = SharedPrefs.getInstance()!!.getUser(this@MyFirebaseMessagingService)

        if (user.userId != null) {

            if (!user.isSalesExecutive!! && !user.isSleepingPartner!!) {
                intent = Intent(this@MyFirebaseMessagingService, UserCategoryActivity::class.java)
            } else
                when (user.isSalesExecutive) {
                    true -> {
                        intent = Intent(this@MyFirebaseMessagingService, DrawerActivity::class.java)
                        intent.putExtra("Category", "Sales")
                        intent.putExtra("type", "message")
                    }
                    false -> {
                        intent = Intent(this@MyFirebaseMessagingService, DrawerActivity::class.java)
                        intent.putExtra("Category", "Sleeping")
                        intent.putExtra("type", "type")
                    }
                }
        } else {
            intent = Intent(this@MyFirebaseMessagingService, SignInActivity::class.java)
            intent.putExtra("Category", "Sales")
            intent.putExtra("type", "type")
        }

        val obj = Gson().toJson(notification)
        intent.putExtra("notification", obj)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val channelId = "Default"
        val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.sleepinglogo)
                .setContentTitle("New Message")
                .setContentText(notification.Message).setAutoCancel(true).setContentIntent(pendingIntent)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        manager.notify(0, builder.build())
    }


}