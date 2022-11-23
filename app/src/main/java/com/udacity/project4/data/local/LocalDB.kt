package com.udacity.project4.data.local


import android.app.Application
import android.content.Context
import androidx.room.Room
import javax.inject.Inject


/**
 * Singleton class that is used to create a reminder db
 */
object LocalDB {

    /**
     * static method that creates a reminder class and returns the DAO of the reminder
     */

    fun createRemindersDao(application: Application): RemindersDao {
        return Room.databaseBuilder(
           application ,
            RemindersDatabase::class.java, "locationReminders.db"
        ).allowMainThreadQueries().build().reminderDao()
    }

}