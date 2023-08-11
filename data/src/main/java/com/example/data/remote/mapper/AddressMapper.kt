package com.example.data.remote.mapper

import com.example.domain.entity.Address
import com.example.domain.entity.Coordinates
import android.location.Address as AddressDTO

fun AddressDTO.toAddress() = Address(
    country = countryName,
    city = if(subAdminArea.isNullOrEmpty()) countryName else subAdminArea,
    addressDetails = getAddressLine(0),
   coordinates =  Coordinates(latitude = latitude, longitude = longitude)
)

