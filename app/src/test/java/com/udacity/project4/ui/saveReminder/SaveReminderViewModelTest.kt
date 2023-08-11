package com.udacity.project4.ui.saveReminder

import com.example.domain.entity.Address
import com.example.domain.entity.Coordinates
import com.example.domain.entity.Error
import com.example.domain.entity.Reminder
import com.example.domain.use_case.AddReminderGeofencingUseCase
import com.example.domain.use_case.GetAddressInfoUseCase
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.ui.TestDispatcherProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


class SaveReminderViewModelTest {

    private val testDispatcherProvider = TestDispatcherProvider()
    private val testScope = TestScope(testDispatcherProvider.testDispatcher)
    lateinit var saveReminderViewModel: SaveReminderViewModel
   private val addReminderGeofencingUseCase: AddReminderGeofencingUseCase = mockk()
    private val getAddressInfoUseCase : GetAddressInfoUseCase = mockk()

    @BeforeEach
    fun setup() {
        saveReminderViewModel =
            SaveReminderViewModel(
                getAddressInfoUseCase = getAddressInfoUseCase,
                addReminderGeofencingUseCase = addReminderGeofencingUseCase,
                dispatchers = testDispatcherProvider
            )
    }

    @Test
    fun `saveReminder() with error then update errorMessage`() = testScope.runTest {
        val errorMessage = "reminder error"

        assertThat(saveReminderViewModel.geofencingAddedSuccessfully.value).isFalse()
        assertThat(saveReminderViewModel.errorMessage.value).isNull()

        coEvery { addReminderGeofencingUseCase.invoke(Reminder(id = 1)) }
            .throws(Error.ReminderError(errorMessage))
        saveReminderViewModel.saveReminder(Reminder(id = 1))

        testDispatcherProvider.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(saveReminderViewModel.geofencingAddedSuccessfully.value).isFalse()
        assertThat(saveReminderViewModel.errorMessage.value).isEqualTo(errorMessage)

    }

    @Test
    fun `saveReminder() with out error then update geofencingAddedSuccessfully true`() =
        testScope.runTest {
            assertThat(saveReminderViewModel.geofencingAddedSuccessfully.value).isFalse()
            assertThat(saveReminderViewModel.errorMessage.value).isNull()

            coEvery { addReminderGeofencingUseCase.invoke(Reminder(id = 1)) } returns Unit
            saveReminderViewModel.saveReminder(Reminder(id = 1))

            testDispatcherProvider.testDispatcher.scheduler.advanceUntilIdle()

            assertThat(saveReminderViewModel.errorMessage.value).isNull()
            assertThat(saveReminderViewModel.geofencingAddedSuccessfully.value).isTrue()
        }

    @Test
    fun `getAddressInfo() with out error then update addressState` () = testScope.runTest {
        val coordinates = Coordinates(1.0, 1.0)
        val address = Address("egypt ","marsa matrouh","marsa matrouh", coordinates = coordinates)
        val addresses = flow {emit(listOf( address ))  }

        coEvery { getAddressInfoUseCase.getAddressInfo(coordinates)} returns addresses
        coEvery { getAddressInfoUseCase.getFirstAddress(addresses.toList()[0]) } returns address
        saveReminderViewModel.getAddressInfo(coordinates = coordinates)

        testDispatcherProvider.testDispatcher.scheduler.advanceUntilIdle()

        assertThat(saveReminderViewModel.addressState.value).isEqualTo(address)
    }

    @Test
    fun `getAddressInfo() with error then update errorMessage` () = testScope.runTest {
        val coordinates = Coordinates(1.0, 1.0)
        val errorMessage = "get address error"

        coEvery { getAddressInfoUseCase.getAddressInfo(coordinates)}
            .throws ( Error.AddressError (errorMessage) )

        saveReminderViewModel.getAddressInfo(coordinates = coordinates)
        testDispatcherProvider.testDispatcher.scheduler.advanceUntilIdle()

        assertThat(saveReminderViewModel.errorMessage.value).isEqualTo(errorMessage)
    }
}

