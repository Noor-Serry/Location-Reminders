package com.example.domain.entity

data class GeofenceData(val id: String, var coordinates:Coordinates? = null )

internal fun reminderToGeofence(reminder: Reminder) = GeofenceData(reminder.id.toString(),reminder.coordinates)
