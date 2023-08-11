package com.example.domain.use_case

import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last


class IsUserLoggedInUseCase (private val userRepository : UserRepository) {

  suspend operator fun invoke() = userRepository.isUserLogin().first() ?: false

}