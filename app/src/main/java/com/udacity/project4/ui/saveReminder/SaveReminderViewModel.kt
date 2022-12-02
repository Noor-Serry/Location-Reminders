package com.udacity.project4.ui.saveReminder



import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.project4.data.ReminderDataSource
import com.udacity.project4.data.dto.ReminderDTO
import com.udacity.project4.utils.Geofence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class SaveReminderViewModel  (val repository : ReminderDataSource) : ViewModel(),KoinComponent{

    private val SUCCESS  = 1
    private val ERROR = 0


     val geofence: Geofence by inject()
     var name :String? = null
     var latitude : Double? = 0.0
     var longitude : Double? = 0.0
     var title = MutableLiveData<String?>()
     var description = MutableLiveData<String?>()


      fun save(){
        val id = (System.currentTimeMillis() % 10000).toString()
       saveReminder(id)
          onClear()
     }

      fun saveReminder(id : String):Int{
      return try{
              val reminderDTO = ReminderDTO(title.value, description.value, name, latitude!!, longitude!! ,id )
              GlobalScope.launch (Dispatchers.IO) {
                  repository.saveReminder(reminderDTO)
              }
              SUCCESS}
          catch (ex : Exception){

           ERROR}

    }

     fun startNewGeofence(id : String):Int{
         return try{
        geofence.startNewGeofence(latitude!!,longitude!!,id)
             SUCCESS }
         catch (ex : Exception){
             ERROR}
    }

    fun onClear() {
        title.value = null
        description.value = null
        name = null
        latitude = null
        longitude = null
    }


}