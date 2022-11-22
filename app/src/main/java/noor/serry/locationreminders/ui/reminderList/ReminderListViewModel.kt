package noor.serry.locationreminders.ui.reminderList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.data.local.RemindersLocalRepository


class ReminderListViewModel (val repository: RemindersLocalRepository) : ViewModel() {

     fun getReminders():LiveData<List<ReminderDTO>> =
          repository.getRemindersAsLiveData()



}