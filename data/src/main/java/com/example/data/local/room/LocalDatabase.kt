package com.example.data.local.room


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.local.room.entity.ReminderEntity


/**
 * The Room Database that contains the reminders table.
 */
@Database(entities = [ReminderEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun reminderDao(): RemindersDao
}