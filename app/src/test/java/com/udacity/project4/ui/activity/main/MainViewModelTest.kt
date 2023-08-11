package com.udacity.project4.ui.activity.main


import com.example.domain.use_case.IsUserLoggedInUseCase
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.ui.TestDispatcherProvider
import com.udacity.project4.ui.repository.FakeUserRepository
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test


class MainViewModelTest {

    private val testDispatcherProvider = TestDispatcherProvider()
    private val testScope = TestScope(testDispatcherProvider.testDispatcher)

   private var mainViewModel :MainViewModel? = null



    private fun setUserLoginState(state : Boolean?){
        val fakeUserRepository = FakeUserRepository()
        fakeUserRepository.userLoginState = state
        val isUserLoggedInUseCase = IsUserLoggedInUseCase(fakeUserRepository)
        mainViewModel = MainViewModel(isUserLoggedInUseCase,testDispatcherProvider)
    }




    @Test
    fun `isUserLoggedIn() when first time open the app then update isUserLoginState to false`() =
        testScope.runTest {
            setUserLoginState(null)

            assertThat( mainViewModel!!.isUserLoginState.value).isNull()

            testDispatcherProvider.testDispatcher.scheduler.advanceUntilIdle()

            assertThat( mainViewModel!!.isUserLoginState.value).isFalse()


        }

    @Test
    fun `isUserLoggedIn() when user logged in then update isUserLoginState to true`() = runTest {
        setUserLoginState(true)

        assertThat( mainViewModel!!.isUserLoginState.value).isNull()
        testDispatcherProvider.testDispatcher.scheduler.advanceUntilIdle()


        assertThat( mainViewModel!!.isUserLoginState.value).isTrue()
    }

    @Test
    fun `isUserLoggedIn() when use logged out then update isUserLoginState to false`() = runTest {
           setUserLoginState(false)

        assertThat( mainViewModel!!.isUserLoginState.value).isNull()
        testDispatcherProvider.testDispatcher.scheduler.advanceUntilIdle()

        assertThat( mainViewModel!!.isUserLoginState.value).isFalse()
    }


}
