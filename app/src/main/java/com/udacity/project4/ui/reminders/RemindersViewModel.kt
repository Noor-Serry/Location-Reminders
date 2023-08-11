package com.udacity.project4.ui.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.use_case.GetAllRemindersUseCase
import com.example.domain.use_case.SetUserLoginStateUseCase
import com.udacity.project4.utils.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
    private val getAllRemindersUseCase: GetAllRemindersUseCase,
    private val setUserLoginStateUseCase: SetUserLoginStateUseCase,
    private val dispatchers : DispatcherProvider
) : ViewModel() {


    private val _remindersState = MutableStateFlow(RemindersState(true, emptyList()))
    val remindersState = _remindersState.asStateFlow()

    init {
        loadReminders()
    }

  private fun loadReminders() {
        viewModelScope.launch(dispatchers.IO) {
            getAllRemindersUseCase().collect { reminders ->
                if (reminders.isEmpty()) _remindersState.update {
                    RemindersState(
                        emptyBoxVisibility = true,
                        reminders = reminders
                    )
                }
                else _remindersState.update {
                    RemindersState(
                        emptyBoxVisibility = false,
                        reminders = reminders
                    )
                }
            }
        }
    }

    fun setUserLoginState(state: Boolean) {
        viewModelScope.launch(dispatchers.IO) {
            setUserLoginStateUseCase(state)
        }
    }


}