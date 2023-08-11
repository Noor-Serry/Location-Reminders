package com.example.data.repository.reminder

import com.example.domain.entity.Reminder
import kotlinx.coroutines.flow.Flow

interface LocalReminder {
    suspend fun saveReminder(reminder: Reminder)
    suspend fun getReminders(): Flow<List<Reminder>>
    suspend fun getReminder(id : Long) : Reminder?
    suspend fun deleteReminderById(id: Long)
}