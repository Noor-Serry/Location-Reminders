package com.udacity.project4.ui.saveReminder


import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.project4.data.dto.ReminderDTO
import com.udacity.project4.data.local.RemindersLocalRepository
import com.udacity.project4.utils.Geofence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class SaveReminderViewModel  (val repository : RemindersLocalRepository, val geofence: Geofence, var context: Context) : ViewModel(){
     var name :String? = null
     var latitude : Double? = 0.0
     var longitude : Double? = 0.0
     var title = MutableLiveData<String?>()
     var description = MutableLiveData<String?>()


      fun save(){
        val id = (System.currentTimeMillis() % 10000).toString()
       saveReminder(id)
         startNewGeofence(id)
          onClear()
     }

    private  fun saveReminder(id : String){
        val reminderDTO = ReminderDTO(title.value, description.value, name, latitude, longitude ,id )
        GlobalScope.launch (Dispatchers.IO){
        repository.saveReminder(reminderDTO)}
    }

    private fun startNewGeofence(id : String){
        geofence.startNewGeofence(latitude!!,longitude!!,id)
    }

    fun onClear() {
        title.value = null
        description.value = null
        name = null
        latitude = null
        longitude = null
    }


}