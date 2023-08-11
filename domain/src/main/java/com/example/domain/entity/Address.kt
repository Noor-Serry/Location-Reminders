package com.example.domain.entity

data class Address(
    val country: String,
    val city: String,
    val addressDetails: String,
    val coordinates: Coordinates
)
