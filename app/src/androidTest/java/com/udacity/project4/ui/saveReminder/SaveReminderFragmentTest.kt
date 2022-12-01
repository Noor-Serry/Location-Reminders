package com.udacity.project4.ui.saveReminder

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavArgs
import androidx.navigation.NavArgsLazy
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.Args
import com.udacity.project4.R
import com.udacity.project4.ui.map.Maps
import com.udacity.project4.ui.map.MapsDirections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
 class SaveReminderFragmentTest{

    @Test
    fun goToReminderList(){
        var bundel = Bundle(3)
        bundel.putDouble("latitude",1.0)
        bundel.putDouble("longitude",1.0)
        bundel.putString("name","name")
        val navController = Mockito.mock(NavController::class.java)
        val scenario = launchFragmentInContainer<SaveReminderFragment>(bundel, R.style.Theme_LocationReminders)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)


        }
        Espresso.onView(ViewMatchers.withId(R.id.saveGeofence)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(
            R.id.action_saveReminderFragment_to_reminderListPage
        )
    }
 }