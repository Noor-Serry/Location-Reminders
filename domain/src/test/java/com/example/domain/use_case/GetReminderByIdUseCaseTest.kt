package com.example.domain.use_case

import com.example.domain.entity.Error
import com.example.domain.entity.Reminder
import com.example.domain.repository.FakeGeofenceReminderRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetReminderByIdUseCaseTest {

    private val fakeReminderRepository = FakeGeofenceReminderRepository()
    lateinit var getReminderByIdUseCase: GetReminderByIdUseCase

    @BeforeEach
    fun setup() {
        getReminderByIdUseCase = GetReminderByIdUseCase(fakeReminderRepository)
    }

    @Test
    fun `getReminderByIdUseCase() with un exist id then throw exception`() = runTest{
        val id = 1L

        assertThrows<Error.ReminderError>  {
             getReminderByIdUseCase(id)
         }

        assertThat(fakeReminderRepository.getReminderCounter).isEqualTo(1)
    }


    @Test
    fun `getReminderByIdUseCase() with exist id then return reminder with same id`() = runTest{
        val id = 1L
        fakeReminderRepository.saveReminder(Reminder(id = id))

        val result = getReminderByIdUseCase(id)

        assertThat(result).isNotNull()
        assertThat(result.id).isEqualTo(id)
        assertThat(fakeReminderRepository.getReminderCounter).isEqualTo(1)
    }
}