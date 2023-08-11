package com.example.data.repository.user

import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(private val localUser: LocalUser) : UserRepository {

    override suspend fun isUserLogin(): Flow<Boolean?> = localUser.isUserLogin()

    override suspend fun setUserLoginState(state: Boolean) = localUser.setUserLoginState(state)


}