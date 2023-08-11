package com.example.domain.use_case

import com.example.domain.repository.GeofenceManager
import javax.inject.Inject

internal class RemoveGeofencingUseCase @Inject constructor(private val geofenceManager: GeofenceManager) {

    operator fun invoke(reminderId: String){
        geofenceManager.removeGeofence(reminderId)
    }
}