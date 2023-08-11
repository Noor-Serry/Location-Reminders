package com.example.domain.repository

import com.example.domain.entity.Address
import com.example.domain.entity.Coordinates
import com.example.domain.entity.Reminder
import kotlinx.coroutines.flow.Flow

interface GeofenceReminderRepository {

 suspend fun saveReminder(reminder: Reminder)
 suspend fun getReminders(): Flow<List<Reminder>>
 suspend fun getReminder(id : Long) : Reminder?
 suspend fun deleteReminderById(id: Long)

 suspend fun getAddressInfo(addressName: String) : Flow<List<Address>>
 suspend fun getAddressInfo(coordinates: Coordinates) : Flow<List<Address>>


}