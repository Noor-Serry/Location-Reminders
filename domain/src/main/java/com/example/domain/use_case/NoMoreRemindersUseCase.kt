package com.example.domain.use_case

import com.example.domain.repository.GeofenceReminderRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject


class NoMoreRemindersUseCase @Inject constructor(private val geofenceReminderRepository: GeofenceReminderRepository){

    suspend operator fun invoke() :Boolean =
          geofenceReminderRepository.getReminders().first().isEmpty()

}