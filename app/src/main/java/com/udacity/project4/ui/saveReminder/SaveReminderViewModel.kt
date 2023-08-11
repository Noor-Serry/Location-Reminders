package com.udacity.project4.ui.saveReminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Address
import com.example.domain.entity.Coordinates
import com.example.domain.entity.Reminder
import com.example.domain.use_case.AddReminderGeofencingUseCase
import com.example.domain.use_case.GetAddressInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.udacity.project4.utils.dispatcher.DispatcherProvider

@HiltViewModel
class SaveReminderViewModel @Inject constructor(
    private val getAddressInfoUseCase: GetAddressInfoUseCase,
    private val addReminderGeofencingUseCase: AddReminderGeofencingUseCase,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    private val _addressState = MutableStateFlow<Address?>(null)
    val addressState = _addressState.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _geofencingAddedSuccessfully = MutableStateFlow(false)
    val geofencingAddedSuccessfully = _geofencingAddedSuccessfully.asStateFlow()

    fun saveReminder(reminder: Reminder) {
        viewModelScope.launch(dispatchers.IO) {
            try {
                addReminderGeofencingUseCase(reminder)
                _geofencingAddedSuccessfully.update { true }
            } catch (e: Exception) {
                _errorMessage.update { e.message }
            }
        }
    }

    fun getAddressInfo(coordinates: Coordinates) {
        viewModelScope.launch(dispatchers.IO) {
            try {
                getAddressInfoUseCase.getAddressInfo(coordinates).collect { addresses ->
                    _addressState.update { getAddressInfoUseCase.getFirstAddress(addresses) }
                }
            } catch (e: Exception) {
                _errorMessage.update { e.message }
            }
        }
    }


}