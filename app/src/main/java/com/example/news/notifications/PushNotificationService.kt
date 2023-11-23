package com.example.news.notifications

import android.Manifest
import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

//service for push notifications
class PushNotificationService: FirebaseMessagingService() {
    private val context = this
    private val TAG = "PushNotification"

    override fun onNewToken(token: String) {
        Log.d(TAG, token)
        super.onNewToken(token)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title: String? = remoteMessage.notification?.title
        val text: String? = remoteMessage.notification?.body

        val CHANNEL_ID = "HEADS_UP_NOTIFICATION"

        val channel = NotificationChannel(CHANNEL_ID, "Heads Up Notification", NotificationManager.IMPORTANCE_HIGH)

        val notification = Notification.Builder(context, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.star_on)
            .setAutoCancel(true)

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(this).notify(1, notification.build())
        }
        super.onMessageReceived(remoteMessage)
    }
}