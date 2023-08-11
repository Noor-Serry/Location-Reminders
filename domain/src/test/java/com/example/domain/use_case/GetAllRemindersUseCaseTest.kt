package com.example.domain.use_case

import com.example.domain.entity.Reminder
import com.example.domain.repository.FakeGeofenceReminderRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class GetAllRemindersUseCaseTest{

    private val fakeGeofenceReminderRepository = FakeGeofenceReminderRepository()
     lateinit var getAllRemindersUseCase :GetAllRemindersUseCase

    @BeforeEach
    fun setup() {
        getAllRemindersUseCase = GetAllRemindersUseCase(fakeGeofenceReminderRepository)
    }

    @Test
    fun `GetAllRemindersUseCase() with empty repository then return flow of empty list`() = runTest {
       val reminders =  getAllRemindersUseCase()

        assertThat(reminders.toList().all { it.isEmpty() }).isTrue()
    }

    @Test
    fun `GetAllRemindersUseCase() with non empty repository then return flow of  list of reminder`() = runTest {
        fakeGeofenceReminderRepository.saveReminder(Reminder(id =1))

        val reminders =  getAllRemindersUseCase()

        assertThat(reminders.toList().all { it.isNotEmpty() }).isTrue()

    }
}