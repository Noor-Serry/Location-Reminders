package com.udacity.project4.ui.broadcast

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.domain.entity.Reminder
import com.udacity.project4.R
import com.udacity.project4.ui.activity.reminder_description.ReminderDescriptionActivity
import com.udacity.project4.utils.Constance.REMINDER_KEY


private const val NOTIFICATION_CHANNEL_ID =  ".channel"

fun sendNotification(context: Context, reminder: Reminder) {
    val notificationManager = context
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
        && notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) == null
    ) {
        val name = context.getString(R.string.app_name)
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            name,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    val intent = Intent(context.applicationContext, ReminderDescriptionActivity::class.java)
     intent.putExtra(REMINDER_KEY,reminder)
   val pendingIntent = PendingIntent.getActivity(context, getUniqueId(),intent,PendingIntent.FLAG_UPDATE_CURRENT)

    val notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.googlemaps)
        .setContentTitle(reminder.title)
        .setContentText(reminder.description)
        .setContentIntent(pendingIntent)
        .setCategory(NotificationCompat.CATEGORY_NAVIGATION)
        .build()

    notificationManager.notify(getUniqueId(), notification)

}


private fun getUniqueId() = ((System.currentTimeMillis() % 10000).toInt())