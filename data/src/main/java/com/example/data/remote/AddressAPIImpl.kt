package com.example.data.remote

import android.location.Geocoder
import android.os.Build
import com.example.data.remote.mapper.toAddress
import com.example.data.repository.reminder.AddressAPI
import com.example.domain.entity.Address
import com.example.domain.entity.Coordinates
import com.example.domain.entity.Error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import kotlin.jvm.Throws

class AddressAPIImpl(private val geocoder: Geocoder) : AddressAPI {

    private val addressesMaxSize = 5
    @Throws
    override suspend fun getAddressInfo(addressName: String): Flow<List<Address>> {
        try {
            val addresses = ArrayList<Address>()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) geocoder.getFromLocationName(
                addressName, addressesMaxSize
            ) {
                it.forEach {
                    addresses.add(it.toAddress())
                }
            }
            else geocoder.getFromLocationName(addressName, addressesMaxSize)
                ?.let { addresses.addAll(it.map { it.toAddress() }) }
            return flow { emit(addresses) }
        } catch (e: IOException) {
            throw Error.AddressError ("there are network error")
        } catch (e: Exception) {
            throw Error.AddressError ( "there are error when getting address")
        }
    }

    @Throws
    override suspend fun getAddressInfo(coordinates: Coordinates): Flow<List<Address>> {
        val addresses = ArrayList<Address>()
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) geocoder.getFromLocation(
                coordinates.latitude, coordinates.longitude, addressesMaxSize
            ) {
                addresses.addAll(it.map { it.toAddress() })
            }
            else geocoder.getFromLocation(
                coordinates.latitude,
                coordinates.longitude,
                addressesMaxSize
            )
                ?.let { addresses.addAll(it.map { it.toAddress() }) }

            return flow { emit(addresses) }
        } catch (e: IOException) {
            throw Error.AddressError ("there are network error")
        } catch (e: Exception) {
            throw Error.AddressError ( "there are error when getting address")
        }
    }


}