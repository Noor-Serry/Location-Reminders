package com.example.domain.entity

sealed class Error ( customMessage : String) : Exception(customMessage){
    class ReminderError ( customMessage : String) : Error(customMessage)
    class GeofencingError( customMessage : String) : Error(customMessage)
    class AddressError( customMessage : String) : Error(customMessage)
}
