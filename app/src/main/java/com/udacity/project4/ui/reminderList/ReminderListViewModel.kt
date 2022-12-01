package com.udacity.project4.ui.reminderList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.udacity.project4.data.ReminderDataSource
import com.udacity.project4.data.dto.ReminderDTO
import com.udacity.project4.data.local.RemindersLocalRepository


class ReminderListViewModel (val repository: ReminderDataSource) : ViewModel() {

     fun getReminders():LiveData<List<ReminderDTO>> =
          repository.getRemindersAsLiveData()



}