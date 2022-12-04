package com.udacity.project4.dataLocal


import com.udacity.project4.data.dto.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.data.dto.ReminderDTO
import com.udacity.project4.data.local.RemindersDatabase
import com.udacity.project4.data.local.RemindersLocalRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
@SmallTest
class RemindersLocalRepositoryTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var repository : RemindersLocalRepository

    @Before
    fun setup() {
       var  remindersDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        ).allowMainThreadQueries().build()
        var dao = remindersDatabase.reminderDao()
        repository= RemindersLocalRepository(dao)
    }

    @Test
    fun saveReminder() = runBlocking {
        val reminderDTOInput = ReminderDTO(
            "title", "description", "name", 0.0,
            0.0, "1")

        repository.saveReminder(reminderDTOInput)
        var result = repository.getReminder("1")
        var reminder = (result as Result.Success).data
        assertThat(reminder!!.title!!.equals("title")).isTrue()
    }

    @Test
    fun deleteReminders() = runBlocking {
        val reminderDTOInput = ReminderDTO(
            "title", "description", "name", 0.0,
            0.0, "1")
        repository.saveReminder(reminderDTOInput)
        repository.deleteAllReminders()
        var result = repository.getReminder("1")
        assertThat(result).isEqualTo(Result.Error("Reminder not found!"))
    }

    @Test
    fun getReminderById() = runBlocking {
        val reminderDTOInput = ReminderDTO(
            "title", "description", "name", 0.0,
            0.0, "1")
        repository.saveReminder(reminderDTOInput)
        var result = repository.getReminder("1")
        assertThat(result).isEqualTo(Result.Success(reminderDTOInput))
    }


}