package com.udacity.project4.ui.reminderList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.project4.data.ReminderDataSource
import com.udacity.project4.data.dto.ReminderDTO
import com.udacity.project4.data.dto.Result
import kotlinx.coroutines.*



class ReminderListViewModel (val repository: ReminderDataSource) : ViewModel() {

    var showSnackBar : MutableLiveData<String> = MutableLiveData()
    var reminders : MutableLiveData<List<ReminderDTO>> = MutableLiveData()

    fun loadReminders(){
       GlobalScope.launch(Dispatchers.IO) {
            when (val result = repository.getReminders()) {
                 is Result.Error ->  showSnackBar.postValue( result .message!!)
                 is Result.Success -> reminders.postValue(result .data)
             }
         }
    }



}