package com.udacity.project4.dataLocal



import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.data.dto.ReminderDTO
import com.udacity.project4.data.local.RemindersDao
import com.udacity.project4.data.local.RemindersDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
@SmallTest
class DaoTest {

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var remindersDatabase: RemindersDatabase
    lateinit var dao: RemindersDao

    @Before
    fun setup() {
        remindersDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemindersDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = remindersDatabase.reminderDao()
    }

    @After
    fun close() {
        remindersDatabase.close()
    }

    @Test
    fun saveReminder() = runBlocking {
        val reminderDTOInput = ReminderDTO(
            "title", "description", "name", 0.0,
            0.0, "1")
        val reminderTheSameId = ReminderDTO(
            "reminderTheSameId", "description", "name", 0.0,
            0.0, "1")
        dao.saveReminder(reminderDTOInput)
        dao.saveReminder(reminderTheSameId)
        val reminderDTOOutput = dao.getReminderById("1")
        assertThat(reminderDTOOutput!!.title!!.equals("reminderTheSameId")).isTrue()
    }

    @Test
    fun deleteRemindersById() = runBlocking {
        val reminderDTOInput1 = ReminderDTO(
            "title", "description", "name", 0.0,
            0.0, "1")
        val reminderDTOInput2 = ReminderDTO(
            "title", "description", "name", 0.0,
            0.0, "2")
        dao.saveReminder(reminderDTOInput1)
        dao.saveReminder(reminderDTOInput2)
        dao.deleteReminderById("1")
        var liveData = dao.getRemindersAsLiveData()
        liveData.observeForever {
            assertThat(it.get(0)==reminderDTOInput2).isTrue()
        }
    }

    @Test
    fun deleteAllReminders() = runBlocking {
        val reminderDTOInput = ReminderDTO(
            "title", "description", "name", 0.0,
            0.0, "1")
        dao.saveReminder(reminderDTOInput)
        dao.deleteReminderById("1")
        var liveData = dao.getRemindersAsLiveData()
        liveData.observeForever {
            assertThat(it).isEmpty()

        }
    }

    @Test
    fun dataNotFound()= runBlocking {
        var data =dao.getReminderById("404")
        if(data!=null)
            assertThat(data).isNotNull()
        assertThat(data).isNull()

    }

}