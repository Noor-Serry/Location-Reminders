package com.example.domain.repository

import com.example.domain.entity.Address
import com.example.domain.entity.Coordinates
import com.example.domain.entity.Error
import com.example.domain.entity.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class FakeGeofenceReminderRepository : GeofenceReminderRepository {

    var reminders = mutableListOf<Reminder>()

    var shouldReturnNetworkErrors: Boolean = false

    var saveReminderCounter = 0
    var getRemindersCounter = 0
    var getReminderCounter = 0
    var deleteReminderByIdCounter = 0
    var getAddressInfoCounter = 0

    override suspend fun saveReminder(reminder: Reminder) {
        saveReminderCounter ++
        reminders.add(reminder)
    }


    override suspend fun getReminders(): Flow<List<Reminder>> {
        getRemindersCounter ++
        return flow { emit(reminders) }
    }


    override suspend fun getReminder(id: Long): Reminder? {
        getReminderCounter++
        return reminders.find { it.id == id }
    }


    override suspend fun deleteReminderById(id: Long) {
        deleteReminderByIdCounter++
        reminders.removeIf { it.id == id }
    }

    var addressesList =   mutableListOf(
        Address(
            "egypt",
            "marsa matrouh",
            "kilo 4",
            Coordinates(30.0, 30.0)
        )
    )

    override suspend fun getAddressInfo(addressName: String): Flow<List<Address>> {
        getAddressInfoCounter++
        if (shouldReturnNetworkErrors)
            throw Error.AddressError("there are error")
        return flow {
            emit(addressesList)
        }
    }

    override suspend fun getAddressInfo(coordinates: Coordinates): Flow<List<Address>> {
        getAddressInfoCounter++
        if (shouldReturnNetworkErrors)
            throw Error.AddressError("there are error")
        return flow {
            emit(addressesList)
        }
    }

}