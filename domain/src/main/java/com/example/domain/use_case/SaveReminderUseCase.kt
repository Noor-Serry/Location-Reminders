package com.example.domain.use_case

import com.example.domain.entity.Error
import com.example.domain.entity.Reminder
import com.example.domain.repository.GeofenceReminderRepository
import javax.inject.Inject
import kotlin.jvm.Throws

internal class SaveReminderUseCase @Inject internal constructor(private val geofenceReminderRepository: GeofenceReminderRepository) {
    @Throws
    suspend operator fun invoke(reminder: Reminder) {
        if (reminder.description.isNullOrBlank() ||
            reminder.title.isNullOrBlank()
        )
            throw Error.ReminderError("There are empty field")
        geofenceReminderRepository.saveReminder(reminder)
    }
}