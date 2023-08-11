package com.example.domain.use_case

import com.example.domain.entity.Coordinates
import com.example.domain.entity.Error
import com.example.domain.entity.GeofenceData
import com.example.domain.entity.Reminder
import com.example.domain.repository.FakeGeofenceManager
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class AddGeofencingUseCaseTest {

    private val geofenceManager = FakeGeofenceManager()
   lateinit var  addGeofencingUseCase : AddGeofencingUseCase

    @BeforeEach
    fun setup() {
        addGeofencingUseCase = AddGeofencingUseCase(geofenceManager)
    }

    @Test
    fun `AddGeofencingUseCase() then invoke` (): Unit = runTest{
        val geofenceData = GeofenceData(
            id = "1",
            coordinates = Coordinates(30.0,30.0)
        )

            addGeofencingUseCase(geofenceData)

        assertThat(geofenceManager.geofences.map { it.id }).contains(geofenceData.id)
    }

    @Test
    fun `AddGeofencingUseCase() with geofence error then throw GeofencingError` (): Unit = runTest{
        val geofenceData = GeofenceData(
            id = "1",
            coordinates = Coordinates(30.0,30.0)
        )
         geofenceManager.geofenceError = true

        assertThrows<Error.GeofencingError> {
            addGeofencingUseCase(geofenceData)
        }

    }

    @Test
    fun `AddGeofencingUseCase() with out coordinates then throw GeofencingError` (): Unit = runTest{
        val geofenceData = GeofenceData(
            id = "1",
            coordinates = null
        )

        assertThrows<Error.GeofencingError> {
            addGeofencingUseCase(geofenceData)
        }

    }



}