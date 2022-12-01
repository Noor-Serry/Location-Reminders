package com.udacity.project4.ui.reminderList


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.data.dto.ReminderDTO
import com.udacity.project4.ui.saveReminder.FackRemindersLocalRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test



class ReminderListViewModelTest {

    lateinit var viewModel: ReminderListViewModel
    var fakeData = ReminderDTO("titel","des","name",1.1,1.1)
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup()= runBlocking {
        var fackRemindersLocalRepository = FackRemindersLocalRepository()

        fackRemindersLocalRepository.saveReminder(fakeData)
        viewModel = ReminderListViewModel(FackRemindersLocalRepository())
    }
    @Test
    fun `check  loading  data`(){

        viewModel.getReminders().observeForever{
            it.forEach {
                assertThat(it).isEqualTo(fakeData)
            }
        }
    }
}