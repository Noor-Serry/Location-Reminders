package com.example.domain.use_case

import com.example.domain.entity.Address
import com.example.domain.entity.Coordinates
import com.example.domain.repository.GeofenceReminderRepository
import kotlinx.coroutines.flow.Flow

class GetAddressInfoUseCase(private val geofenceReminderRepository: GeofenceReminderRepository) {

    suspend fun getAddressInfo(addressName: String) :Flow<List<Address>>
       =     geofenceReminderRepository.getAddressInfo(addressName)


    suspend fun getAddressInfo(coordinates: Coordinates) =
        geofenceReminderRepository.getAddressInfo(coordinates)

    fun getFirstAddress(addresses : List<Address>)  = addresses.firstOrNull()

}