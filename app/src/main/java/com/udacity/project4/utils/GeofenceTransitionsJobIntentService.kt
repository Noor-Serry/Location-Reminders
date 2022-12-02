package com.udacity.project4.utils

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import com.udacity.project4.data.ReminderDataItem
import com.udacity.project4.data.dto.ReminderDTO
import com.udacity.project4.data.local.RemindersLocalRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import com.udacity.project4.data.dto.Result
import com.udacity.project4.data.local.LocalDB.createRemindersDao
import com.udacity.project4.data.local.RemindersDao
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GeofenceTransitionsJobIntentService : JobIntentService(), CoroutineScope{

    private var coroutineJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + coroutineJob

    companion object {
        private const val JOB_ID = 573

        //        TODO: call this to start the JobIntentService to handle the geofencing transition events
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(
                context,
                GeofenceTransitionsJobIntentService::class.java, JOB_ID,
                intent)
        }

        }




    override fun onHandleWork(intent: Intent) {
            var id = intent.getStringExtra("id").toString()
           sendNotification(id)
    }

    //TODO: get the request id of the current geofence
    private fun sendNotification(requestId:String) {

        val remindersLocalRepository = RemindersLocalRepository(createRemindersDao(application))
        GlobalScope.launch(Dispatchers.IO) {
            val result = remindersLocalRepository.getReminder(requestId)
            if (result is Result.Success<ReminderDTO>) {
                val reminderDTO = result.data
                sendNotification(
                    this@GeofenceTransitionsJobIntentService, ReminderDataItem(
                        reminderDTO.title,
                        reminderDTO.description,
                        reminderDTO.name,
                        reminderDTO.latitude,
                        reminderDTO.longitude,
                        reminderDTO.id
                    )
                )
            }
        }
    }

}




