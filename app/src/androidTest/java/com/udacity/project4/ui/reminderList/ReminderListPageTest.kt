package com.udacity.project4.ui.reminderList

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.R
import com.udacity.project4.ui.map.Maps
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
 class ReminderListPageTest{



     @Test
     fun goToMapFragment(){
         val navController = Mockito.mock(NavController::class.java)
         val scenario = launchFragmentInContainer<ReminderListPage>(Bundle(), R.style.Theme_LocationReminders)
         scenario.onFragment {
             Navigation.setViewNavController(it.view!!, navController)
         }
         Espresso.onView(ViewMatchers.withId(R.id.imageButton)).perform(ViewActions.click())
         Mockito.verify(navController).navigate(
             R.id.action_reminderListPage_to_maps
         )
     }

 }