package com.example.domain.use_case


import com.example.domain.entity.Reminder
import com.example.domain.repository.FakeGeofenceReminderRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import  kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RemoveReminderByIdUseCaseTest {


    private val fakeReminderRepository = FakeGeofenceReminderRepository()
    private lateinit var removeReminderByIdUseCase: RemoveReminderByIdUseCase

    @BeforeEach
    fun setup() {
        removeReminderByIdUseCase = RemoveReminderByIdUseCase(fakeReminderRepository)
    }

    @Test
    fun `removeReminderByIdUseCase() with  exist id then remove reminder with same id`() =
        runTest {
            val id = 1L
            fakeReminderRepository.saveReminder(Reminder(id = id))

            removeReminderByIdUseCase(id)

            assertThat(fakeReminderRepository.deleteReminderByIdCounter).isEqualTo(1)
            assertThat(fakeReminderRepository.getReminder(id)).isNull()
        }

    @Test
    fun `removeReminderByIdUseCase() with  unExist id then does not remove any reminder`() =
        runTest {
            val unExistId = 1L
            val notes = arrayListOf(Reminder(id = 2), Reminder(id = 3))

            notes.forEach {
                fakeReminderRepository.saveReminder(it)
            }

            removeReminderByIdUseCase(unExistId)

            assertThat(fakeReminderRepository.deleteReminderByIdCounter).isEqualTo(1)
            assertThat(fakeReminderRepository.getReminders().toList()).contains(notes)
        }
}