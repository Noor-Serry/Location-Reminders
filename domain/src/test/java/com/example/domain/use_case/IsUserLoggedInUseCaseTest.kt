package com.example.domain.use_case

import com.example.domain.repository.FakeUserRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class IsUserLoggedInUseCaseTest {

    val fakeUserRepository = FakeUserRepository()
    lateinit var isUserLoggedInUseCase : IsUserLoggedInUseCase

    @BeforeEach
    fun setup() {
        isUserLoggedInUseCase = IsUserLoggedInUseCase(fakeUserRepository)
    }

    @Test
    fun `IsUserLoggedInUseCase() with null state in repo then return false`() = runTest {
        assertThat(isUserLoggedInUseCase()).isFalse()
        assertThat(fakeUserRepository.isUserLoginCounter).isEqualTo(1)
    }

    @Test
    fun `IsUserLoggedInUseCase() with true state in repo then return true`() = runTest {
        fakeUserRepository.userLoginState = true
        assertThat(isUserLoggedInUseCase()).isTrue()
        assertThat(fakeUserRepository.isUserLoginCounter).isEqualTo(1)
    }

    @Test
    fun `IsUserLoggedInUseCase() with false state in repo then return false`() = runTest {
        fakeUserRepository.userLoginState = false
        assertThat(isUserLoggedInUseCase()).isFalse()
        assertThat(fakeUserRepository.isUserLoginCounter).isEqualTo(1)
    }
}