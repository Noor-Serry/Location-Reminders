package com.udacity.project4.ui.reminderList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.project4.data.ReminderDataSource
import com.udacity.project4.data.dto.ReminderDTO
import com.udacity.project4.data.dto.Result
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class ReminderListViewModel (val repository: ReminderDataSource) : ViewModel() {

     fun getReminders():LiveData<List<ReminderDTO>> {
         var liveData = MutableLiveData<List<ReminderDTO>>()
          viewModelScope.launch {
              var result =repository.getReminders()
             when(result){
                  is Result.Success -> liveData.value =result.data
                  else -> {liveData.value = ArrayList<ReminderDTO>()}
              } }
              return liveData
     }

        suspend fun getReminderById(id : String) : Result<ReminderDTO>{
        return  repository.getReminder(id)
    }


}