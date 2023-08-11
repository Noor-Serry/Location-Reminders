package com.example.data.repository.user

import kotlinx.coroutines.flow.Flow

interface LocalUser {
    suspend fun isUserLogin() : Flow<Boolean?>
    suspend fun setUserLoginState(state: Boolean)
}