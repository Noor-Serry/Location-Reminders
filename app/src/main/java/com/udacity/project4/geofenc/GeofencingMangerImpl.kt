package com.udacity.project4.geofenc

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.example.domain.entity.Error
import com.example.domain.entity.GeofenceData
import com.example.domain.repository.GeofenceManager
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.udacity.project4.ui.broadcast.GeofenceBroadcastReceiver
import kotlinx.coroutines.tasks.await
import kotlin.jvm.Throws

class GeofencingMangerImpl(private val context: Context) :
    GeofenceManager {


   private val geofencingClient = LocationServices.getGeofencingClient(context)


    private fun createGeofence(geofenceData: GeofenceData) =
        Geofence.Builder().setRequestId(geofenceData.id)
            .setCircularRegion(geofenceData.coordinates!!.latitude, geofenceData.coordinates!!.longitude,100F)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setExpirationDuration(Geofence.NEVER_EXPIRE)
            .build()



    private fun geofencePendingIntent() : PendingIntent {
        val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
        return  PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )}



    private fun createGeofencingRequest(geofenceData: GeofenceData)
         =  GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER  )
                . addGeofence(createGeofence(geofenceData))
        }.build()

    @Throws
    override suspend fun addGeofence(
        geofenceData: GeofenceData
    ) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ){
            try {
                geofencingClient.addGeofences(createGeofencingRequest( geofenceData),
                geofencePendingIntent()).await()}
            catch (e:Exception){
                throw Error.GeofencingError("There are error while adding geofence")
            }
        }
        else  throw Error.GeofencingError("Please allow location permission")
    }

    override fun removeGeofence(reminderId: String) {
        geofencingClient.removeGeofences(listOf(reminderId))
    }


}