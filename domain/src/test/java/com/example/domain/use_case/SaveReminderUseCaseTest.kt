package com.example.domain.use_case

import com.example.domain.entity.Coordinates
import com.example.domain.entity.Error
import com.example.domain.entity.Reminder
import com.example.domain.repository.FakeGeofenceReminderRepository
import com.google.common.truth.Truth.assertThat
import  kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SaveReminderUseCaseTest {

    private lateinit var saveReminderUseCase: SaveReminderUseCase
   private  lateinit var  fakeReminderRepository : FakeGeofenceReminderRepository

    @BeforeEach
    fun setUp() {
        fakeReminderRepository = FakeGeofenceReminderRepository()
        saveReminderUseCase = SaveReminderUseCase(fakeReminderRepository)
    }


    @Test
    fun `SaveReminderUseCase() then invoke`() = runTest {
        val reminder = Reminder(
            id = 1,
            title = "title",
            description = "description",
            locationName = "locationName",
            coordinates = Coordinates(1.0, 1.0)
        )

        saveReminderUseCase(reminder)
        val result = fakeReminderRepository.getReminder(1)

        assertThat(fakeReminderRepository.saveReminderCounter).isEqualTo(1)
        assertThat(result).isNotNull()
    }

    @Test
    fun `SaveReminderUseCase() with  null description  and null title then throw exception`() :Unit = runTest {
        val reminder = Reminder(
            id = 1,
            title = null,
            description = null,
            locationName = "locationName",
            coordinates = Coordinates(1.0, 1.0)
        )

        assertThrows<Error.ReminderError> {
            saveReminderUseCase(reminder)
        }
    }


    @Test
    fun `SaveReminderUseCase() with  null description then throw exception`() :Unit = runTest {
        val reminder = Reminder(
            id = 1,
            title = "title",
            description = null,
            locationName = "locationName",
            coordinates = Coordinates(1.0, 1.0)
        )

        assertThrows<Error.ReminderError> {
            saveReminderUseCase(reminder)
        }

    }

    @Test
    fun `SaveReminderUseCase() with  null title then throw exception`() :Unit = runTest {
        val reminder = Reminder(
            id = 1,
            title = null,
            description = "description",
            locationName = "locationName",
            coordinates = Coordinates(1.0, 1.0)
        )

         assertThrows<Error.ReminderError> {
             saveReminderUseCase(reminder)
        }

    }
}