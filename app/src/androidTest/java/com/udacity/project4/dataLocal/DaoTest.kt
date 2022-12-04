package com.udacity.project4.dataLocal

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.data.dto.ReminderDTO
import com.udacity.project4.data.local.RemindersDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi

import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DaoTest {

    private lateinit var database: RemindersDatabase

    @Before
    fun initDb() {
        database = Room.inMemoryDatabaseBuilder(
            getApplicationContext(),
            RemindersDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun saveReminderAndGetById() = runBlocking {
        val reminder = ReminderDTO("title","des","name",0.0,0.0)
        database.reminderDao().saveReminder(reminder)
        val loaded = database.reminderDao().getReminderById(reminder.id)
        assertThat(loaded as ReminderDTO).isEqualTo(reminder)
    }

    @Test
    fun deleteAllReminderAndGetReminders()= runBlocking {
        val reminder = ReminderDTO("title","des","name",0.0,0.0)
        database.reminderDao().saveReminder(reminder)
        database.reminderDao().deleteAllReminders()
        val allReminder =database.reminderDao().getReminders()
        assertThat(allReminder.size).isEqualTo(0)
    }
}
