package com.udacity.project4.saveReminder


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.udacity.project4.data.ReminderDataSource
import com.udacity.project4.data.dto.ReminderDTO
import com.udacity.project4.data.dto.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class FackRemindersLocalRepository() : ReminderDataSource {

     var reminders = mutableListOf<ReminderDTO>()
     var observableReminders  = MutableLiveData<List<ReminderDTO>>(reminders)
      var shouldReturnError = false

     fun setReturnError(value: Boolean) {
        shouldReturnError = value
     }

     override suspend fun getReminders(): Result<List<ReminderDTO>> = withContext(Dispatchers.IO) {
         if (shouldReturnError) {
             return@withContext Result.Error("Test exception")
         }
         return@withContext   Result.Success(reminders)
     }

     override suspend fun saveReminder(reminder: ReminderDTO) {
         reminders.add(reminder)
         observableReminders.postValue(reminders)
     }


     override suspend fun getReminder(id: String): Result<ReminderDTO> = withContext(Dispatchers.IO) {
         if (shouldReturnError) {
                return@withContext Result.Error("Test exception")
         }
         reminders.forEach {
             if(it.id == id)
                 return@withContext Result.Success(it)
         }

         return@withContext Result.Error("Could not find task")
     }

    override suspend fun deleteAllReminders() {
        reminders.clear()
        observableReminders.postValue(reminders)
    }

    override fun getRemindersAsLiveData(): LiveData<List<ReminderDTO>> {
        return observableReminders
    }

}

