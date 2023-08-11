package com.udacity.project4.di

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.room.Room
import com.example.data.local.room.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    @Singleton
    fun provideLocalDatabase(application: Application): LocalDatabase {
        return Room.databaseBuilder(
            application ,
            LocalDatabase::class.java, "locationReminders.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideReminderDao(localDatabase : LocalDatabase) = localDatabase.reminderDao()

    @Provides
    @Singleton
    fun providerDataStore(@ApplicationContext context : Context) =   context.createDataStore(name = "dataStore")


}