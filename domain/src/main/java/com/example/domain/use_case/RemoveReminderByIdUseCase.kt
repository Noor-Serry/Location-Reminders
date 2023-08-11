package com.example.domain.use_case

import com.example.domain.repository.GeofenceReminderRepository
import javax.inject.Inject

internal class RemoveReminderByIdUseCase @Inject constructor (private val repository: GeofenceReminderRepository) {

    suspend operator fun invoke (id : Long) = repository.deleteReminderById(id)
}
