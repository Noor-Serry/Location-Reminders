package com.udacity.project4.ui.saveReminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.data.ReminderDataSource
import com.udacity.project4.data.dto.ReminderDTO
import com.udacity.project4.data.dto.Result
import org.junit.Test


class FackRemindersLocalRepository() : ReminderDataSource {

     var reminders = mutableListOf<ReminderDTO>()
     var observableReminders  = MutableLiveData<List<ReminderDTO>>(reminders)

    override suspend fun getReminders(): Result<List<ReminderDTO>> {
        return try {
            Result.Success(observableReminders.value!!)
        } catch (ex: Exception) {
            Result.Error(ex.localizedMessage)
        }
    }

    override suspend fun saveReminder(reminder: ReminderDTO) {
        reminders.add(reminder)
        observableReminders.postValue(reminders)
    }

    override suspend fun getReminder(id: String): Result<ReminderDTO> {
            var reminder :ReminderDTO?=null
            observableReminders.value?.forEach { reminder =if (it.id==id) it else null }
            if (reminder != null) {
                return Result.Success(reminder!!)
            } else {
                return  Result.Error("Reminder not found!")
            }


    }

    override suspend fun deleteAllReminders() {
        reminders.clear()
        observableReminders.postValue(reminders)
    }


    override fun getRemindersAsLiveData(): LiveData<List<ReminderDTO>> {
        return observableReminders
    }
}