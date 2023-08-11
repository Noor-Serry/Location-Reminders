package com.example.domain.use_case

import com.example.domain.repository.GeofenceReminderRepository

class GetAllRemindersUseCase(private val geofenceReminderRepository: GeofenceReminderRepository) {

suspend operator fun invoke() = geofenceReminderRepository.getReminders()
}