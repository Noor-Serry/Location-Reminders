package com.example.domain.use_case

import com.example.domain.repository.UserRepository

class SetUserLoginStateUseCase(private val userRepository : UserRepository) {

    suspend operator fun invoke(state : Boolean) = userRepository.setUserLoginState(state)
}