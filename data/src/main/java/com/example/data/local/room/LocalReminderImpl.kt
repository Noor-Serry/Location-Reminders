package com.example.data.local.room


import com.example.data.local.room.entity.toReminder
import com.example.data.local.room.entity.toReminderEntity
import com.example.data.repository.reminder.LocalReminder
import com.example.domain.entity.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalReminderImpl(private val dao: RemindersDao) : LocalReminder {

    override suspend fun saveReminder(reminder: Reminder) =
        dao.saveReminder(reminder.toReminderEntity())

    override suspend fun getReminders(): Flow<List<Reminder>> =
        dao.getReminders().map { it.map { it.toReminder() } }

    override suspend fun getReminder(id: Long): Reminder? = dao.getReminderById(id)?.toReminder()

    override suspend fun deleteReminderById(id: Long) = dao.deleteReminderById(id)


}