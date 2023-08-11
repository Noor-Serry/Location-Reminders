package com.example.domain.use_case
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoveReminderGeofencingUseCase @Inject internal constructor(
    private val removeReminderByIdUseCase : RemoveReminderByIdUseCase,
    private val removeGeofencingUseCase : RemoveGeofencingUseCase
){
    suspend operator fun invoke(id : Long){
        removeReminderByIdUseCase(id)
        removeGeofencingUseCase(id.toString())
    }

}




