package com.example.domain.use_case

import com.example.domain.entity.Error
import com.example.domain.entity.Reminder
import com.example.domain.entity.reminderToGeofence
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.Throws

@Singleton
class AddReminderGeofencingUseCase @Inject  internal constructor(
private val saveReminderUseCase: SaveReminderUseCase ,
 private val addGeofencingUseCase : AddGeofencingUseCase,
private val removeReminderByIdUseCase: RemoveReminderByIdUseCase
) {
     @Throws
    suspend operator fun invoke(reminder: Reminder){
        try {
            saveReminderUseCase(reminder)
            addGeofencingUseCase(reminderToGeofence(reminder))
        }catch ( e : Error.GeofencingError){
            removeReminderByIdUseCase(reminder.id)
            throw e
        }
        catch (e :Exception){
            throw e
        }
    }

}




