package com.udacity.project4.ui

import android.app.Application
import com.udacity.project4.utils.*

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class UsersApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@UsersApp)
            modules(listOf(
                saveReminderViewModel,
                repositoryModel,
                daoModel,
                geofence,
                reminderListViewModel,
                recyclerAdapter
            ))
        }
    }
}