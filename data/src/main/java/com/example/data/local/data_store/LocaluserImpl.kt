package com.example.data.local.data_store


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import com.example.data.local.utils.Constants.IS_LOGIN_KEY
import com.example.data.repository.user.LocalUser
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.flow


class LocalUserImpl(private val dataStore: DataStore<Preferences>) : LocalUser{


    override suspend fun isUserLogin(): Flow<Boolean?> = flow{
        dataStore.data.collect{
            emit(it[preferencesKey(IS_LOGIN_KEY)])
        }
    }


    override suspend fun setUserLoginState(state: Boolean) {
        dataStore.edit { it[preferencesKey(IS_LOGIN_KEY)] = state }
    }


}