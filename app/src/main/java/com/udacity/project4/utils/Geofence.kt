package com.udacity.project4.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices

class Geofence (val context : Context) {

  private lateinit var  geofence :Geofence
   private lateinit var  pendingIntent : PendingIntent
   private lateinit var  geofenceRequest :  GeofencingRequest

    @SuppressLint("SuspiciousIndentation")

        fun startNewGeofence(latitude: Double, longitude: Double, id: String) {
            initializeGeofence(latitude, longitude, id)
            initializePendingIntent(id)
            initializeGeofencingRequest()
            val geofencingClient = LocationServices.getGeofencingClient(context)
            if (checkPermission())
                geofencingClient.addGeofences(geofenceRequest, pendingIntent)

        }


    private fun initializeGeofence(latitude : Double , longitude : Double ,id : String){
         geofence =  Geofence.Builder().setRequestId(id)
            .setCircularRegion(latitude,longitude,100.0f)
            .setExpirationDuration(10*24*3600*1000)
            .setTransitionTypes( Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_DWELL)
             .setLoiteringDelay(1000)
            .build()
    }

    private fun  initializePendingIntent(id : String){
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        intent.putExtra("id",id)
         pendingIntent =   PendingIntent.getBroadcast(context, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun initializeGeofencingRequest(){
        geofenceRequest =  GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER or GeofencingRequest.INITIAL_TRIGGER_DWELL)
                . addGeofence(geofence)
        }.build()
    }

    private fun checkPermission():Boolean{
       return (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }
}