package com.udacity.project4.di

import com.example.domain.repository.GeofenceReminderRepository
import com.example.domain.repository.UserRepository
import com.example.domain.use_case.GetAddressInfoUseCase
import com.example.domain.use_case.GetAllRemindersUseCase
import com.example.domain.use_case.IsUserLoggedInUseCase
import com.example.domain.use_case.SetUserLoginStateUseCase
import com.udacity.project4.utils.dispatcher.DefaultDispatcherProvider
import com.udacity.project4.utils.dispatcher.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent



@Module
@InstallIn(ViewModelComponent::class)
class UsesCase {

    @Provides
    fun provideIsUserLoggedInUseCase(userRepository: UserRepository) = IsUserLoggedInUseCase(userRepository)

    @Provides
    fun provideSetUserLoginStateUseCase(userRepository: UserRepository) = SetUserLoginStateUseCase(userRepository)

    @Provides
    fun provideCoroutineDispatcher() : DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    fun provideGetAllRemindersUseCase(geofenceReminderRepository: GeofenceReminderRepository) = GetAllRemindersUseCase(geofenceReminderRepository)

    @Provides
    fun provideGetAddressInfoUseCase(geofenceReminderRepository: GeofenceReminderRepository) = GetAddressInfoUseCase(geofenceReminderRepository)

}