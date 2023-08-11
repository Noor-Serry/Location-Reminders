package com.example.domain.use_case

import com.example.domain.entity.Coordinates
import com.example.domain.entity.Error
import com.example.domain.entity.Reminder
import com.example.domain.entity.reminderToGeofence
import com.example.domain.repository.FakeGeofenceManager
import com.example.domain.repository.FakeGeofenceReminderRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class AddReminderGeofencingUseCaseTest {

    private val fakeReminderRepository = FakeGeofenceReminderRepository()
    private val geofenceManager = FakeGeofenceManager()

    private lateinit var addReminderGeofencingUseCase: AddReminderGeofencingUseCase

    @BeforeEach
    fun setUp() {

        val saveReminderUseCase = SaveReminderUseCase(fakeReminderRepository)
        val addGeofencingUseCase = AddGeofencingUseCase(geofenceManager)
        val removeReminderByIdUseCase = RemoveReminderByIdUseCase(fakeReminderRepository)

        addReminderGeofencingUseCase = AddReminderGeofencingUseCase(
            saveReminderUseCase,
            addGeofencingUseCase,
            removeReminderByIdUseCase
        )
    }

    @Test
    fun `AddReminderGeofencingUseCase() then invoke saveReminder && addGeofencing`() = runTest {

        val reminder = Reminder(
            id = 1,
            locationName = "marsa matroh",
            title = "title",
            description = "description",
            coordinates = Coordinates(30.0, 30.0)
        )

        addReminderGeofencingUseCase(reminder)

        assertThat(fakeReminderRepository.saveReminderCounter).isEqualTo(1)
        assertThat(fakeReminderRepository.reminders).contains(reminder)

        assertThat(geofenceManager.addGeofenceCounter).isEqualTo(1)
        assertThat(geofenceManager.geofences).contains(reminderToGeofence(reminder))
    }

    @Test
    fun `AddReminderGeofencingUseCase() with empty title reminder then throw ReminderError`() {
        runTest {
            val reminder = Reminder(id = 1, title = "")

            assertThrows<Error.ReminderError> {
                addReminderGeofencingUseCase(reminder)
            }
        }
    }


    @Test
    fun `AddReminderGeofencingUseCase() with add null coordinates then throw GeofencingError && invoke remove reminder`() {
        runTest {
            val reminder = Reminder(
                id = 1,
                locationName = "marsa matroh",
                title = "title",
                description = "description",
                coordinates = null
            )


            assertThrows<Error.GeofencingError> {
                addReminderGeofencingUseCase(reminder)
            }

            assertThat(fakeReminderRepository.deleteReminderByIdCounter).isEqualTo(1)
            assertThat(fakeReminderRepository.reminders.map { it.id }).doesNotContain(reminder.id)
        }
    }




}