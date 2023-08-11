package com.example.domain.use_case

import com.example.domain.entity.GeofenceData
import com.example.domain.repository.FakeGeofenceManager
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class RemoveGeofencingUseCaseTest {
    private val fakeGeofenceManager = FakeGeofenceManager()
   private lateinit var removeGeofencingUseCase : RemoveGeofencingUseCase

    @BeforeEach
    fun setup() {
    removeGeofencingUseCase = RemoveGeofencingUseCase(fakeGeofenceManager)
    }

    @Test
    fun `removeGeofencingUseCase() with exist id then do not remove any geofence`() = runTest {
        fakeGeofenceManager.geofences = mutableListOf(GeofenceData("1"))

        removeGeofencingUseCase("1")

        assertThat(fakeGeofenceManager.geofences).doesNotContain(GeofenceData("1"))
        assertThat(fakeGeofenceManager.removeGeofenceCounter).isEqualTo(1)
    }

    @Test
    fun `removeGeofencingUseCase() with un exist id then do not remove any geofence`() = runTest {
        fakeGeofenceManager.geofences = mutableListOf(GeofenceData("1"))

        removeGeofencingUseCase("2")

        assertThat(fakeGeofenceManager.geofences).isEqualTo(mutableListOf(GeofenceData("1")))
        assertThat(fakeGeofenceManager.removeGeofenceCounter).isEqualTo(1)
    }
}