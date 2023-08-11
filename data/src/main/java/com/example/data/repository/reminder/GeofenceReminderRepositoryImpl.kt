package com.example.data.repository.reminder


import com.example.domain.entity.Coordinates
import com.example.domain.entity.Error
import com.example.domain.entity.Reminder
import com.example.domain.repository.GeofenceReminderRepository
import kotlinx.coroutines.flow.Flow
import java.lang.Exception


class GeofenceReminderRepositoryImpl(
    private val localReminder: LocalReminder,
    private val addressAPI: AddressAPI
    ) : GeofenceReminderRepository {

    override suspend fun saveReminder(reminder: Reminder)  {
         try {
            localReminder.saveReminder(reminder)

        } catch (e : Exception){
         throw Error.ReminderError("error saving reminder")
        }
    }

    override suspend fun getReminders(): Flow<List<Reminder>> = localReminder.getReminders()


    override suspend fun getReminder(id: Long): Reminder? = localReminder.getReminder(id)
    override suspend fun deleteReminderById(id: Long) = localReminder.deleteReminderById(id)


    override suspend fun getAddressInfo(addressName: String) =
        addressAPI.getAddressInfo(addressName)


    override suspend fun getAddressInfo(coordinates: Coordinates) =
        addressAPI.getAddressInfo(coordinates)



}
