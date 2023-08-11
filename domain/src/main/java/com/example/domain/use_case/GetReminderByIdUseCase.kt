package com.example.domain.use_case

import com.example.domain.entity.Error
import com.example.domain.repository.GeofenceReminderRepository
import javax.inject.Inject
import kotlin.jvm.Throws

class GetReminderByIdUseCase @Inject constructor(private val geofenceReminderRepository: GeofenceReminderRepository) {

    @Throws
    suspend operator fun invoke(id : Long)  =
     geofenceReminderRepository.getReminder(id) ?:
     throw Error.ReminderError("could not find this reminder")

}