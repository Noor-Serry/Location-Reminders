package com.udacity.project4.ui.reminders

import com.example.domain.entity.Reminder
import com.example.domain.use_case.GetAllRemindersUseCase
import com.example.domain.use_case.SetUserLoginStateUseCase
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.ui.TestDispatcherProvider
import com.udacity.project4.ui.repository.FakeGeofenceReminderRepository
import com.udacity.project4.ui.repository.FakeUserRepository
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

class RemindersViewModelTest {

    private val testDispatcherProvider = TestDispatcherProvider()
    private val testScope = TestScope(testDispatcherProvider.testDispatcher)

    private val fakeGeofenceReminderRepository = FakeGeofenceReminderRepository()
    private val fakeUserRepository = FakeUserRepository()
    lateinit var reminderViewModel: RemindersViewModel

       private fun setup(allReminders : MutableList<Reminder>) {
           fakeGeofenceReminderRepository.reminders = allReminders
           val getAllRemindersUseCase = GetAllRemindersUseCase(fakeGeofenceReminderRepository)
           val setUserLoginStateUseCase = SetUserLoginStateUseCase(fakeUserRepository)
           reminderViewModel = RemindersViewModel(
               getAllRemindersUseCase = getAllRemindersUseCase,
               setUserLoginStateUseCase = setUserLoginStateUseCase,
               dispatchers = testDispatcherProvider
           )
       }

    @Test
    fun `loadReminders() with empty reminders in repo then then update emptyBoxVisibility to be true`() =
        testScope.runTest {
            setup(mutableListOf())

              val defaultReminderState = RemindersState(true, emptyList())
            assertThat(reminderViewModel.remindersState.value).isEqualTo(defaultReminderState)

            testDispatcherProvider.testDispatcher.scheduler.advanceUntilIdle()

            val updatedReminderState =  RemindersState(true, emptyList())
            assertThat(reminderViewModel.remindersState.value).isEqualTo(updatedReminderState)
        }

    @Test
    fun `loadReminders() with reminders in repo then then update emptyBoxVisibility to be false`() =
        testScope.runTest {
            val reminders = mutableListOf(Reminder(id = 1),Reminder(id = 2),Reminder(id = 3))
            setup(reminders)

            val defaultReminderState = RemindersState(true, emptyList())
            assertThat(reminderViewModel.remindersState.value).isEqualTo(defaultReminderState)

            testDispatcherProvider.testDispatcher.scheduler.advanceUntilIdle()

            val updatedReminderState =  RemindersState(false, reminders = reminders)
            assertThat(reminderViewModel.remindersState.value).isEqualTo(updatedReminderState)
        }

    @Test
    fun `setUserLoginState() with user login in true then save true`()= testScope.runTest {
        setup(mutableListOf())
        val loginState = true

        assertThat(fakeUserRepository.userLoginState).isNull()
        reminderViewModel.setUserLoginState(loginState)

        testDispatcherProvider.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(fakeUserRepository.userLoginState).isTrue()
    }

    @Test
    fun `setUserLoginState() with user login in false then save false`()= testScope.runTest {
        setup(mutableListOf())
        val loginState = false

        assertThat(fakeUserRepository.userLoginState).isNull()
        reminderViewModel.setUserLoginState(loginState)

        testDispatcherProvider.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(fakeUserRepository.userLoginState).isFalse()
    }


}