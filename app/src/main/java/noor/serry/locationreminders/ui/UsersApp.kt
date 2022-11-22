package noor.serry.locationreminders.ui

import android.app.Application
import noor.serry.locationreminders.*
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