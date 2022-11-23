package com.udacity.project4.utils

import com.udacity.project4.data.local.LocalDB
import com.udacity.project4.data.local.RemindersLocalRepository
import com.udacity.project4.ui.reminderList.RecyclerAdapter
import com.udacity.project4.ui.reminderList.ReminderListViewModel
import com.udacity.project4.ui.saveReminder.SaveReminderViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val saveReminderViewModel  = module {
    viewModel { SaveReminderViewModel(repository = get(), geofence = get(),androidContext()) }}
val repositoryModel = module {
    single {
        RemindersLocalRepository(remindersDao = get())
    }
}
val reminderListViewModel = module {
    viewModel{
        ReminderListViewModel(repository = get())
    }
}
val daoModel = module{
    single {
        LocalDB.createRemindersDao(androidApplication())
    }
}
val geofence = module {
    single {
        Geofence(androidContext())
    }


}
val recyclerAdapter = module {
    single {
        RecyclerAdapter()
    }
}
