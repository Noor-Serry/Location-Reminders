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

     val geofence: Geofence by inject()
     var name :String? = null
     var latitude : Double? = 0.0
     var longitude : Double? = 0.0
     var title = MutableLiveData<String?>()
     var description = MutableLiveData<String?>()
     var showLoading = MutableLiveData<Boolean>()
     var showMessage = MutableLiveData<Boolean>()

      fun save(){
          if(dataNotCompleted())
              showMessage.value = false
          else {
              showMessage.value = true
              val id = (System.currentTimeMillis() % 10000).toString()
              saveReminder(id)
              startNewGeofence(id)
              onClear()
     }}

     fun dataNotCompleted():Boolean{
        return title.value.isNullOrEmpty()||description.value.isNullOrEmpty()
    }

    fun saveReminder(id : String){
                  showLoading.value = true
              GlobalScope.launch (Dispatchers.IO) {
                  val reminderDTO = ReminderDTO(title.value, description.value, name, latitude!!, longitude!! ,id )
                  repository.saveReminder(reminderDTO)
                  showLoading.postValue( false)
    } }

     fun startNewGeofence(id : String){
        geofence.startNewGeofence(latitude!!,longitude!!,id)
    }

    fun onClear() {
        title.value = null
        description.value = null
        name = null
        latitude = null
        longitude = null
        showMessage = MutableLiveData<Boolean>()
    }


}