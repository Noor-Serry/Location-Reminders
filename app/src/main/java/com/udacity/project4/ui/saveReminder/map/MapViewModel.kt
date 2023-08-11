package com.udacity.project4.ui.saveReminder.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Address
import com.example.domain.entity.Coordinates
import com.example.domain.use_case.GetAddressInfoUseCase
import com.udacity.project4.utils.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val getAddressInfoUseCase: GetAddressInfoUseCase,
    private val dispatchers : DispatcherProvider
) : ViewModel() {

    private val _addressesState = MutableStateFlow<List<Address>>(emptyList())
    val addressesState = _addressesState.asStateFlow()

    private val _addressState = MutableStateFlow<Address?>(null)
    val addressState = _addressState.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _currentCoordinates = MutableStateFlow<Coordinates?>(null)
    val currentCoordinates = _currentCoordinates.asStateFlow()


    fun onSearch(text: String) {
        viewModelScope.launch(dispatchers.IO) {
            try {
                getAddressInfoUseCase.getAddressInfo(text).collect{addresses ->
                    _addressesState.update { addresses }
                }
            }
            catch (e : Exception){
                _errorMessage.update { e.message }
            }
        }
    }

    fun onMapClick(coordinates: Coordinates) {
        _currentCoordinates.value = coordinates
        viewModelScope.launch(dispatchers.IO) {
            try {
                getAddressInfoUseCase.getAddressInfo(coordinates).collect{addresses ->
                    _addressState.update { getAddressInfoUseCase.getFirstAddress(addresses) }
                }
            }
            catch (e : Exception){
                _errorMessage.update { e.message }
            }
        }
    }

}