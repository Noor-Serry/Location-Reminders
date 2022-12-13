package com.udacity.project4.ui.saveReminder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule

import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.LiveDataTestUtils.LiveDataTestUtils.getOrAwaitValue
import com.udacity.project4.saveReminder.FackRemindersLocalRepository
import com.udacity.project4.saveReminder.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.DelayController
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.coroutines.ContinuationInterceptor


@MediumTest
@ExperimentalCoroutinesApi
class SaveReminderViewModelTest{
    lateinit var viewModel: SaveReminderViewModel
    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    @Before
    fun setup(){
        viewModel = SaveReminderViewModel(FackRemindersLocalRepository() )
    }

    @After
    fun down(){
        viewModel.onClear()
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
    fun check_loading() {
        viewModel.title.value = "any Text"
        viewModel.description.value = "any text"
        viewModel.latitude = 0.0 ; viewModel.longitude = 0.0
        viewModel.name = "any text"

        (mainCoroutineRule.coroutineContext[ContinuationInterceptor]!! as DelayController).pauseDispatcher()
        viewModel.saveReminder("id")

        val  valueBeforeCoroutine = viewModel.showLoading.value
        assertThat(valueBeforeCoroutine).isTrue()

        (mainCoroutineRule.coroutineContext[ContinuationInterceptor]!! as DelayController).resumeDispatcher()
        val valueAfterCoroutine = viewModel.showLoading.value
        assertThat(valueAfterCoroutine).isFalse()
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