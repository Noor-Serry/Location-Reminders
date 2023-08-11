package com.udacity.project4.ui.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.use_case.IsUserLoggedInUseCase
import com.udacity.project4.utils.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
   private val dispatchers : DispatcherProvider
) : ViewModel() {

    private val _isUserLoginState = MutableStateFlow<Boolean?>(null)
    val isUserLoginState = _isUserLoginState.asStateFlow()


    init {
        isUserLoggedIn()
    }

    private fun isUserLoggedIn() {
        viewModelScope.launch(dispatchers.IO) {
            _isUserLoginState.update { isUserLoggedInUseCase() }
        }
    }

}

