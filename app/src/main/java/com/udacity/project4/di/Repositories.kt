package com.udacity.project4.di

import android.content.Context
import android.location.Geocoder
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.udacity.project4.geofenc.GeofencingMangerImpl
import com.example.data.local.data_store.LocalUserImpl
import com.example.data.local.room.LocalReminderImpl
import com.example.data.local.room.RemindersDao
import com.example.data.remote.AddressAPIImpl
import com.example.data.repository.reminder.AddressAPI
import com.example.data.repository.reminder.GeofenceReminderRepositoryImpl
import com.example.data.repository.reminder.LocalReminder
import com.example.data.repository.user.LocalUser
import com.example.data.repository.user.UserRepositoryImpl
import com.example.domain.repository.GeofenceManager
import com.example.domain.repository.GeofenceReminderRepository
import com.example.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Repositories {

    @Singleton
    @Provides
    fun provideUserRepository(localUser: LocalUser): UserRepository = UserRepositoryImpl(localUser)

    @Singleton
    @Provides
    fun provideLocalUser(dataStore: DataStore<Preferences>): LocalUser = LocalUserImpl(dataStore)


    @Singleton
    @Provides
    fun provideGeofenceReminderRepository(
        localReminder: LocalReminder,
        addressAPI: AddressAPI,
    ): GeofenceReminderRepository =
        GeofenceReminderRepositoryImpl(localReminder = localReminder,
            addressAPI = addressAPI)

    @Singleton
    @Provides
    fun provideLocalReminder(remindersDao: RemindersDao): LocalReminder =
        LocalReminderImpl(remindersDao)

    @Singleton
    @Provides
    fun provideAddressAPI(geocoder: Geocoder): AddressAPI = AddressAPIImpl(geocoder)

    @Singleton
    @Provides
    fun provideGeocoder(@ApplicationContext context: Context): Geocoder = Geocoder(context)

    @Singleton
    @Provides
    fun provideGeofencingManager(@ApplicationContext context: Context): GeofenceManager = GeofencingMangerImpl(context)

}