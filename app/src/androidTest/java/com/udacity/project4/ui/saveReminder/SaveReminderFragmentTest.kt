package com.udacity.project4.ui.saveReminder

import android.os.Bundle
import android.view.View
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.TypeTextAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import com.udacity.project4.R
import org.hamcrest.Matchers
import org.junit.runner.RunWith
import org.mockito.Mockito


@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
 class SaveReminderFragmentTest{
 lateinit var scenario: FragmentScenario<SaveReminderFragment>
 lateinit var  navController: NavController
  @Before
  fun setup(){
   var bundel = Bundle(3)
   bundel.putDouble("latitude",1.0)
   bundel.putDouble("longitude",1.0)
   bundel.putString("name","name")
    navController = Mockito.mock(NavController::class.java)
   scenario = launchFragmentInContainer<SaveReminderFragment>(bundel,
    R.style.Theme_LocationReminders)
   scenario.onFragment {
    Navigation.setViewNavController(it.view!!, navController)
   }
  }

 @Test
 fun showSnackbarWhenTitleOrDescriptionIsEmpty(){
  scenario.onFragment { it.viewModel.showMessage.value = false }
   onView(withId(com.google.android.material.R.id.snackbar_text))
    .check(matches(withText("Please Complete Data")))
  }

 @Test
 fun showToastMessageWhenCompleteData(){
  var view :View? = null
  scenario.onFragment {
   it.viewModel.showMessage.value = true
   view = it.view
  }

  onView(withText("Save New Geofence"))
   .inRoot(withDecorView(Matchers.not(view)))
   .check(matches(isDisplayed()))
 }

 @Test
 fun goToReminderListPage(){
  scenario.onFragment {
   it.viewModel.showMessage.value = true
  }
  Mockito.verify(navController).navigate(
   R.id.action_saveReminderFragment_to_reminderListPage
  )
 }
 }