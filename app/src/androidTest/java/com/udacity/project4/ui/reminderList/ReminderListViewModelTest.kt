package com.udacity.project4.ui.reminderList

import com.udacity.project4.data.dto.*
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.data.dto.ReminderDTO
import com.udacity.project4.ui.saveReminder.FackRemindersLocalRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test



class ReminderListViewModelTest {
    private val ID = "id"

    lateinit var viewModel: ReminderListViewModel
    var fakeData = ReminderDTO("titel","des","name",1.1,1.1,ID)
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup()= runBlocking {
        viewModel = ReminderListViewModel(FackRemindersLocalRepository())
    }

    @Test
    fun checkLoadingData()= runBlocking {
        viewModel.repository.saveReminder(fakeData)
        viewModel.getReminders().observeForever{
            it.forEach {
                assertThat(it).isEqualTo(fakeData)
            }
        }
    }

   @Test
   fun checkLoadingDataById()= runBlocking {
       viewModel.repository.saveReminder(fakeData)
       var result =viewModel.getReminderById(ID)
       var reminder = (result as  Result.Success).data
       assertThat(reminder).isEqualTo(fakeData)
   }

    @Test
    fun shouldReturnErrorWhenReminderNoFound()= runBlocking {
        var result =viewModel.getReminderById(ID)
        assertThat(result).isEqualTo(Result.Error("Reminder not found!"))
    }


}