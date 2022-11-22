package noor.serry.locationreminders.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import noor.serry.locationreminders.utils.GeofenceTransitionsJobIntentService.Companion.enqueueWork

/**
 * Triggered by the Geofence.  Since we can have many Geofences at once, we pull the request
 * ID from the first Geofence, and locate it within the cached data in our Room DB
 *
 * Or users can add the reminders and then close the app, So our app has to run in the background
 * and handle the geofencing in the background.
 * To do that you can use https://developer.android.com/reference/android/support/v4/app/JobIntentService to do that.
 *
 */

class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
         Toast.makeText(context,"kcsa",Toast.LENGTH_SHORT).show()
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        val geofenceTransition = geofencingEvent!!.geofenceTransition
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER)
        {
            enqueueWork(context,intent)
        }
        }
    }

