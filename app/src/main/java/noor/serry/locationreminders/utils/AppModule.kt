package noor.serry.locationreminders

import com.udacity.project4.locationreminders.data.local.LocalDB
import com.udacity.project4.locationreminders.data.local.RemindersLocalRepository
import noor.serry.locationreminders.ui.reminderList.RecyclerAdapter

import noor.serry.locationreminders.ui.reminderList.ReminderListViewModel
import noor.serry.locationreminders.ui.saveReminder.SaveReminderViewModel
import noor.serry.locationreminders.utils.Geofence
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
