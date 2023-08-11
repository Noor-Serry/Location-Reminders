package com.example.data.repository.reminder

import com.example.domain.entity.Address
import com.example.domain.entity.Coordinates
import kotlinx.coroutines.flow.Flow

interface AddressAPI {

    suspend fun getAddressInfo(addressName: String) : Flow<List<Address>>
    suspend fun getAddressInfo(coordinates: Coordinates) : Flow<List<Address>>

}