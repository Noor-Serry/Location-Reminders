package com.udacity.project4.ui.login

import com.example.domain.use_case.SetUserLoginStateUseCase
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.ui.TestDispatcherProvider
import com.udacity.project4.ui.repository.FakeUserRepository
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LogInViewModelTest {

    private val testDispatcherProvider = TestDispatcherProvider()
    private val testScope = TestScope(testDispatcherProvider.testDispatcher)
    val fakeUserRepository = FakeUserRepository()
    lateinit var logInViewModel : LogInViewModel

    @BeforeEach
    fun setup() {
      val setUserLoginStateUseCase = SetUserLoginStateUseCase(fakeUserRepository)
        logInViewModel = LogInViewModel(setUserLoginStateUseCase,testDispatcherProvider)
    }

    @Test
    fun `setUserLoginState() with user login in true then save true`()= testScope.runTest {
        val loginState = true

        logInViewModel.setUserLoginState(loginState)

        assertThat(fakeUserRepository.userLoginState).isNull()
        testDispatcherProvider.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(fakeUserRepository.userLoginState).isTrue()
    }

    @Test
    fun `setUserLoginState() with user login in false then save false`()= testScope.runTest {
        val loginState = false

        logInViewModel.setUserLoginState(loginState)

        assertThat(fakeUserRepository.userLoginState).isNull()
        testDispatcherProvider.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(fakeUserRepository.userLoginState).isFalse()
    }

}