package com.example.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

 class FakeUserRepository :UserRepository {
     var userLoginState : Boolean? = null

    var isUserLoginCounter = 0
    var setUserLoginStateCounter = 0
    override suspend fun isUserLogin(): Flow<Boolean?>{
        isUserLoginCounter++
        return flow{emit(userLoginState)}
    }

    override suspend fun setUserLoginState(state: Boolean) {
        setUserLoginStateCounter++
        userLoginState =  state
    }
}