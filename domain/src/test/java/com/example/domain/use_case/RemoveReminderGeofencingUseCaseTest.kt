package com.example.domain.use_case

import com.example.domain.entity.Reminder
import com.example.domain.entity.reminderToGeofence
import com.example.domain.repository.FakeGeofenceManager
import com.example.domain.repository.FakeGeofenceReminderRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class RemoveReminderGeofencingUseCaseTest {

    private val fakeGeofenceManager = FakeGeofenceManager()
    private val fakeGeofenceReminderRepository = FakeGeofenceReminderRepository()
    private lateinit var removeReminderGeofencingUseCase : RemoveReminderGeofencingUseCase

    @BeforeEach
    fun setup() {
       val removeReminderByIdUseCase = RemoveReminderByIdUseCase(fakeGeofenceReminderRepository)
       val  removeGeofencingUseCase = RemoveGeofencingUseCase(fakeGeofenceManager)
        removeReminderGeofencingUseCase = RemoveReminderGeofencingUseCase(
            removeReminderByIdUseCase,
            removeGeofencingUseCase
        )
    }

    @Test
    fun `RemoveReminderGeofencingUseCase() with not exist id then do not remove any geofence or reminder`() = runTest {
       val reminder = Reminder(id = 1)
        val geofenceData = reminderToGeofence(reminder)
        fakeGeofenceManager.geofences.add(geofenceData)
        fakeGeofenceReminderRepository.reminders.add(reminder)

        removeReminderGeofencingUseCase(2)

        assertThat(fakeGeofenceManager.geofences).contains(geofenceData)
        assertThat(fakeGeofenceReminderRepository.reminders).contains(reminder)
    }

    @Test
    fun `RemoveReminderGeofencingUseCase() with exist id then remove  geofence and reminder with same id`() = runTest {
        val reminder = Reminder(id = 1)
        val geofenceData = reminderToGeofence(reminder)
        fakeGeofenceManager.geofences.add(geofenceData)
        fakeGeofenceReminderRepository.reminders.add(reminder)

        removeReminderGeofencingUseCase(1)

        assertThat(fakeGeofenceManager.geofences).doesNotContain(geofenceData)
        assertThat(fakeGeofenceReminderRepository.reminders).doesNotContain(reminder)
    }

}