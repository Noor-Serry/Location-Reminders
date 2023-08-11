package com.example.domain.use_case

import com.example.domain.entity.Error
import com.example.domain.entity.GeofenceData
import com.example.domain.repository.GeofenceManager
import javax.inject.Inject
import kotlin.jvm.Throws

internal class AddGeofencingUseCase @Inject constructor(private val geofenceManager: GeofenceManager) {

    @Throws
    internal suspend operator fun invoke(geofence: GeofenceData) {
        if(geofence.coordinates==null)
            throw Error.GeofencingError("cant add geofence with out coordinates")
        geofenceManager.addGeofence(geofence)
    }
}