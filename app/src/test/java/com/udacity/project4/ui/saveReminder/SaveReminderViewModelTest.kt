package com.udacity.project4.ui.saveReminder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.utils.Geofence
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class SaveReminderViewModelTest{
    lateinit var viewModel: SaveReminderViewModel

    private val SUCCESS  = 1
    private val ERROR = 0


    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        viewModel = SaveReminderViewModel(FackRemindersLocalRepository() )
    }

     @Test
     fun `insert empty filed  return error`(){
         viewModel.latitude = null
         viewModel.longitude = 0.0
         viewModel.name = "NAME"
         viewModel.description.postValue( "des")
         val result = viewModel.saveReminder("id")
         assertThat(result).isEqualTo(ERROR)
     }

    @Test
    fun `did not start new geofence with out longitude or latitude `(){
        viewModel.latitude = null
        val result =  viewModel.startNewGeofence("id")
        assertThat(result).isEqualTo(ERROR)
    }

    @Test
    fun `on clear fun is work`(){
        viewModel.onClear()
        assertThat(viewModel.latitude).isNull()
        assertThat(viewModel.name).isNull()
    }
}