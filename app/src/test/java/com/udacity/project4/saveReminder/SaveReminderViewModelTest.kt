package com.udacity.project4.ui.saveReminder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.LiveDataTestUtils.LiveDataTestUtils.getOrAwaitValue
import com.udacity.project4.saveReminder.FackRemindersLocalRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi

import org.junit.Before
import org.junit.Rule
import org.junit.Test


@MediumTest
@ExperimentalCoroutinesApi
class SaveReminderViewModelTest{
    lateinit var viewModel: SaveReminderViewModel

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        viewModel = SaveReminderViewModel(FackRemindersLocalRepository() )
    }


    @Test
    fun showMessageShouldReturnFalseWhenDataNotCompleted(){
        viewModel.save()
        val showMessageValue =viewModel.showMessage.getOrAwaitValue()
        assertThat(showMessageValue).isFalse()
    }

    @Test
    fun onClearIsWork(){
        viewModel.onClear()
        assertThat(viewModel.latitude).isNull()
        assertThat(viewModel.name).isNull()
    }


    @Test
    fun dataNotCompletedShouldReturnTrue(){
        assertThat( viewModel.dataNotCompleted()).isTrue()
    }

    @Test
    fun dataNotCompletedShouldReturnFalse(){
       viewModel.description.value = "des"
        viewModel.title.value = "title"
        assertThat(viewModel.dataNotCompleted()).isFalse()
    }


}