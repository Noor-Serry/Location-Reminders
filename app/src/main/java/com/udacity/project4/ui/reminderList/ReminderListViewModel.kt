package com.udacity.project4.ui.reminderList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.project4.data.ReminderDataSource
import com.udacity.project4.data.dto.ReminderDTO
import com.udacity.project4.data.dto.Result
import kotlinx.coroutines.*


class ReminderListViewModel (val repository: ReminderDataSource) : ViewModel() {

    fun getReminders():LiveData<List<ReminderDTO>> =
        repository.getRemindersAsLiveData()

        suspend fun getReminderById(id : String) : Result<ReminderDTO>{
        return  repository.getReminder(id)
    }


}