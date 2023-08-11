package com.udacity.project4.ui.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.udacity.project4.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GeofenceTransitionsService : Service() {


    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
     startForegroundService()
        refreshMyLocation()
        return START_STICKY
    }


    private fun startForegroundService(){
        val notificationManager = this
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, "my_channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("My Service")
            .setContentText("Running...")


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "my_channel_id"
            val channelName = "My Channel Name"
            val channelDescription = "My Channel Description"
            val importance = NotificationManager.IMPORTANCE_LOW

            val notificationChannel =
                NotificationChannel(channelId, channelName, importance).apply {
                    description = channelDescription
                }
            notificationManager.createNotificationChannel(notificationChannel)
        }

        startForeground(3, builder.build())
    }

    @SuppressLint("MissingPermission")
    private fun refreshMyLocation() {

        coroutineScope.launch {
            while (true) {
                delay(5 * 1000)
                fusedLocationClient.getCurrentLocation(
                    CurrentLocationRequest.Builder()
                        .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                        .build(), null)
            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

}







