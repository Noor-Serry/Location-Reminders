package com.example.domain.use_case

import com.example.domain.repository.FakeUserRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class SetUserLoginStateUseCaseTest {

   val fakeUserRepository = FakeUserRepository()
    lateinit var setUserLoginStateUseCase : SetUserLoginStateUseCase
    @BeforeEach
    fun setup() {
        setUserLoginStateUseCase = SetUserLoginStateUseCase(fakeUserRepository)
    }
    @Test
    fun `SetUserLoginStateUseCase() with true then save true`() = runTest{
        setUserLoginStateUseCase(true)
        assertThat(fakeUserRepository.userLoginState).isTrue()
    }

    @Test
    fun `SetUserLoginStateUseCase() with false then save false`() = runTest{
        setUserLoginStateUseCase(false)
        assertThat(fakeUserRepository.userLoginState).isFalse()
    }
}