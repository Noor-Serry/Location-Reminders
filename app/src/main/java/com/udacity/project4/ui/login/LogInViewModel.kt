package com.udacity.project4.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.use_case.SetUserLoginStateUseCase
import com.udacity.project4.utils.dispatcher.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val setUserLoginStateUseCase: SetUserLoginStateUseCase,
    private val dispatchers : DispatcherProvider
) : ViewModel() {


    fun setUserLoginState(state: Boolean) {
        viewModelScope.launch(dispatchers.IO) {
            setUserLoginStateUseCase(state)
        }
    }

}