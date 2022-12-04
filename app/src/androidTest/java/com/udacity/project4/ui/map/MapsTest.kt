package com.udacity.project4.ui.map

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.R
import com.udacity.project4.ui.reminderList.ReminderListPage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
 class MapsTest{

    @Test
    fun goToFindLocationFragment(){
       val navController = Mockito.mock(NavController::class.java)
       val scenario = launchFragmentInContainer<Maps>(Bundle(), R.style.Theme_LocationReminders)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.locationBySearch)).perform(click())
        Mockito.verify(navController).navigate(
            R.id.action_maps_to_findLocation
        )
    }

    @Test
    fun goToSaveReminder(){
        val navController = Mockito.mock(NavController::class.java)
        val scenario = launchFragmentInContainer<Maps>(Bundle(), R.style.Theme_LocationReminders)
        var directions = MapsDirections.actionMapsToSaveReminderFragment(
            1.0f,1.0f,"name")
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
            it.directions = directions
        }
        Espresso.onView(ViewMatchers.withId(R.id.save)).perform(click())
        Mockito.verify(navController)
            .navigate(directions)
    }

    @Test
    fun showToastAndDontNavigate(){
        val navController = Mockito.mock(NavController::class.java)
        val scenario = launchFragmentInContainer<Maps>(Bundle(), R.style.Theme_LocationReminders)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.save)).perform(click())
        Mockito.verify(navController)
    }

 }