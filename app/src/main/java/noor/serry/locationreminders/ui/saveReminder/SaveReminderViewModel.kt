package noor.serry.locationreminders.ui.saveReminder


import android.app.Application
import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.local.RemindersLocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import noor.serry.locationreminders.utils.Geofence


class SaveReminderViewModel  ( val repository : RemindersLocalRepository , val geofence: Geofence,var context: Context) : ViewModel(){
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