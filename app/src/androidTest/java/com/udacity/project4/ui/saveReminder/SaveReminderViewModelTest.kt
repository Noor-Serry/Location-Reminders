package com.udacity.project4.ui.saveReminder

import android.os.Bundle
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.TypeTextAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard

import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.Matchers

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class SaveReminderViewModelTest{
    lateinit var viewModel: SaveReminderViewModel

    private val SUCCESS  = 1
    private val ERROR = 0
    lateinit var scenario: FragmentScenario<SaveReminderFragment>

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        var bundel = Bundle(3)
        bundel.putDouble("latitude",1.0)
        bundel.putDouble("longitude",1.0)
        bundel.putString("name","name")
        val navController = Mockito.mock(NavController::class.java)
        scenario = launchFragmentInContainer<SaveReminderFragment>(bundel, R.style.Theme_LocationReminders)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)

        }

        viewModel = SaveReminderViewModel(FackRemindersLocalRepository() )
    }



    @Test
    fun didNotStartNewGeofenceWithOutLongitudeOrLatitude (){
        viewModel.latitude = null
        val result =  viewModel.startNewGeofence("id")
        assertThat(result).isEqualTo(ERROR)
    }

    @Test
    fun onClearIsWork(){
        viewModel.onClear()
        assertThat(viewModel.latitude).isNull()
        assertThat(viewModel.name).isNull()
    }

    @Test
    fun showSnackbarWhenTitleOrDescriptionIsEmpty(){

        onView(withId(R.id.saveGeofence)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText("Please Complete Data")))
    }

    @Test
    fun showToastMessageWhenCompleteData(){
        var view: View? = null
        scenario.onFragment {
            view = it.view!!
        }
        onView(withId(R.id.title)).perform(TypeTextAction("title"),closeSoftKeyboard())
         onView(withId(R.id.des)).perform(TypeTextAction("des"),closeSoftKeyboard())
        Thread.sleep(2000)
        onView(withId(R.id.saveGeofence)).perform(click())
        onView(withText("Save New Geofence"))
            .inRoot(withDecorView(Matchers.not(view)))
            .check(matches(isDisplayed()))
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