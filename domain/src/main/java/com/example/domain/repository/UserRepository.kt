package com.example.domain.repository


import kotlinx.coroutines.flow.Flow

interface UserRepository  {
    suspend fun isUserLogin() : Flow<Boolean?>
    suspend fun setUserLoginState(state: Boolean)
}