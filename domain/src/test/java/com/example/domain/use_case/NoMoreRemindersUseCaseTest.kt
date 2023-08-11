package com.example.domain.use_case

import com.example.domain.entity.Reminder
import com.example.domain.repository.FakeGeofenceReminderRepository
import com.google.common.truth.Truth.assertThat
import  kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class NoMoreRemindersUseCaseTest {

    private lateinit var  fakeGeofenceReminderRepository : FakeGeofenceReminderRepository
    lateinit var noMoreRemindersUseCase: NoMoreRemindersUseCase

    @BeforeEach
    fun setup() {
        fakeGeofenceReminderRepository = FakeGeofenceReminderRepository()
        noMoreRemindersUseCase = NoMoreRemindersUseCase(fakeGeofenceReminderRepository)
    }


    @Test
    fun `NoMoreRemindersUseCase()  then return return true`() = runTest{

        val result = noMoreRemindersUseCase()

        assertThat(result).isTrue()
    }

    @Test
    fun `NoMoreRemindersUseCase() with non empty repository then return return false`() = runTest{
             fakeGeofenceReminderRepository.saveReminder(Reminder(id =1))

        val result = noMoreRemindersUseCase()

        assertThat(result).isFalse()
    }

}