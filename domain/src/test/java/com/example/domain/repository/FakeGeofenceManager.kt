package com.example.domain.repository

import com.example.domain.entity.Error
import com.example.domain.entity.GeofenceData
import com.example.domain.entity.Reminder

class FakeGeofenceManager : GeofenceManager {

    var geofences = mutableListOf<GeofenceData>()
    var addGeofenceCounter = 0
    var removeGeofenceCounter = 0

    var geofenceError = false

    override suspend fun addGeofence(geofenceData: GeofenceData) {
        if (geofenceError)
            throw Error.GeofencingError(" adding geofence error")
        addGeofenceCounter++
        geofences.add(geofenceData)
    }

    override fun removeGeofence(GeofenceId: String) {
        removeGeofenceCounter++
        geofences.removeIf { GeofenceId ==it.id }
    }

}