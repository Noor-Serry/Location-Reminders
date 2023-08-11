package com.udacity.project4.ui.broadcast

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.domain.use_case.GetReminderByIdUseCase
import com.example.domain.use_case.NoMoreRemindersUseCase
import com.example.domain.use_case.RemoveReminderGeofencingUseCase
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent
import com.udacity.project4.ui.service.GeofenceTransitionsService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Triggered by the GeofencingManger.  Since we can have many Geofences at once, we pull the request
 * ID from the first GeofencingManger, and locate it within the cached data in our Room DB
 *
 * Or users can add the reminders and then close the app, So our app has to run in the background
 * and handle the geofencing in the background.
 * To do that you can use https://developer.android.com/reference/android/support/v4/app/JobIntentService to do that.
 *
 */
@AndroidEntryPoint
class GeofenceBroadcastReceiver : BroadcastReceiver() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var getReminderByIdUseCase: GetReminderByIdUseCase

    @Inject
    lateinit var removeReminderGeofencingUseCase: RemoveReminderGeofencingUseCase

    @Inject
    lateinit var noMoreRemindersUseCase: NoMoreRemindersUseCase

    @SuppressLint("SuspiciousIndentation")
    override fun onReceive(context: Context, intent: Intent) {

        val geofencingEvent = GeofencingEvent.fromIntent(intent)!!
        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceStatusCodes
                .getStatusCodeString(geofencingEvent.errorCode)
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            return
        }

        for (geofence in geofencingEvent.triggeringGeofences!!) {
            coroutineScope.launch {
                sendNotification(context, getReminderByIdUseCase(geofence.requestId.toLong())!!)
                removeReminderGeofencingUseCase(geofence.requestId.toLong())
                if (noMoreRemindersUseCase())
                    context.stopService(Intent(context, GeofenceTransitionsService::class.java))
            }
        }
    }


}



