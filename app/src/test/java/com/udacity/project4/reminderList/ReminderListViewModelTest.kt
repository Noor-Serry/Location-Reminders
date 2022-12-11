package com.udacity.project4.ui.reminderList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.LiveDataTestUtils.LiveDataTestUtils.getOrAwaitValue
import com.udacity.project4.data.dto.ReminderDTO
import com.udacity.project4.saveReminder.FackRemindersLocalRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ReminderListViewModelTest {

    lateinit var viewModel: ReminderListViewModel
    val fackRemindersLocalRepository = FackRemindersLocalRepository()

    var fakeData = ReminderDTO("titel","des","name",1.1,1.1,"id")
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup()= runBlocking {
        fackRemindersLocalRepository.saveReminder(fakeData)
        viewModel = ReminderListViewModel(fackRemindersLocalRepository)

    }

    @Test
    fun shouldReturnError () {
        fackRemindersLocalRepository.setReturnError(true)
        viewModel.loadReminders()
        var result =viewModel.showSnackBar.getOrAwaitValue()
       assertThat(result).isEqualTo("Test exception")
       }

    @Test
    fun check_loading_Reminders(){
        viewModel.loadReminders()
        val result = viewModel.reminders.getOrAwaitValue()
        assertThat(result.get(0)).isEqualTo(fakeData)
    }

}