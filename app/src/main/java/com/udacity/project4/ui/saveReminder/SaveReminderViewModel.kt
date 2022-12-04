package com.udacity.project4.ui.saveReminder



import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.udacity.project4.R
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

      fun saveReminder(id : String):Int{
      return try{
              showLoading.value = false
              GlobalScope.launch (Dispatchers.IO) {
                  showLoading.postValue( true)
                  val reminderDTO = ReminderDTO(title.value, description.value, name, latitude!!, longitude!! ,id )
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
         showMessage = MutableLiveData<Boolean>()
    }


}