package com.udacity.project4.ui.saveReminder


import androidx.lifecycle.MutableLiveData
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.data.ReminderDataSource
import com.udacity.project4.data.dto.ReminderDTO
import com.udacity.project4.data.dto.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.junit.Test


class FackRemindersLocalRepository() : ReminderDataSource {

     var reminders = mutableListOf<ReminderDTO>()
     var observableReminders  = MutableLiveData<List<ReminderDTO>>(reminders)


            override suspend fun getReminders(): Result<List<ReminderDTO>> = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(reminders)
        }
        catch (ex: Exception) {
            Result.Error(ex.localizedMessage)
        }
    }
    override suspend fun saveReminder(reminder: ReminderDTO) {
        reminders.add(reminder)
        observableReminders.postValue(reminders)
    }


        override suspend fun getReminder(id: String): Result<ReminderDTO> = withContext(Dispatchers.IO) {
            try {
                reminders.forEach {
                    if (it.id == id)
                        return@withContext Result.Success(it)
                }
                return@withContext Result.Error("Reminder not found!")
            } catch (e: Exception) {
                return@withContext Result.Error(e.localizedMessage)
            }
        }

    override suspend fun deleteAllReminders() {
        reminders.clear()
        observableReminders.postValue(reminders)
    }

}