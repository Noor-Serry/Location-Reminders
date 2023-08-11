package com.example.data.local.room


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.data.local.room.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the reminders table.
 */
@Dao
interface RemindersDao {

    @Query("SELECT * FROM reminders")
    fun getReminders(): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminders where id = :id")
    suspend fun getReminderById(id: Long): ReminderEntity?

    @Insert
    suspend fun saveReminder(reminder: ReminderEntity)

    @Query("DELETE FROM reminders WHERE id = :id")
    fun deleteReminderById(id: Long)



}