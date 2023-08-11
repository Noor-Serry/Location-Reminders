package com.example.domain.repository

import com.example.domain.entity.GeofenceData

interface GeofenceManager {
    suspend fun addGeofence(geofenceData: GeofenceData)
    fun removeGeofence(reminderId: String)
}