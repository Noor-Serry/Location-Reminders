package com.udacity.project4.data

import androidx.lifecycle.LiveData
import com.udacity.project4.data.dto.ReminderDTO
import com.udacity.project4.data.dto.Result

/**
 * Main entry point for accessing reminders data.
 */
interface ReminderDataSource {
    suspend fun getReminders(): Result<List<ReminderDTO>>
    suspend fun saveReminder(reminder: ReminderDTO)
    suspend fun getReminder(id: String): Result<ReminderDTO>
    suspend fun deleteAllReminders()
    fun getRemindersAsLiveData() : LiveData<List<ReminderDTO>>
}