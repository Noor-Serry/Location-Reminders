package com.udacity.project4.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_MUTABLE
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
            if (checkPermission()) {
                geofencingClient.addGeofences(geofenceRequest,pendingIntent)
            }
        }


    private fun initializeGeofence(latitude : Double , longitude : Double ,id : String){
         geofence =  Geofence.Builder().setRequestId(id)
            .setCircularRegion(latitude,longitude,100.0f)
            .setTransitionTypes( Geofence.GEOFENCE_TRANSITION_ENTER )
             .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()
    }



    private fun  initializePendingIntent(id : String){
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        intent.putExtra("id",id)
         pendingIntent =   PendingIntent.getBroadcast(context, 0, intent,FLAG_MUTABLE)

    }

    private fun initializeGeofencingRequest(){
        geofenceRequest =  GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER )
                . addGeofence(geofence)
        }.build()
    }

    private fun checkPermission():Boolean{
       return (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED)
    }
}