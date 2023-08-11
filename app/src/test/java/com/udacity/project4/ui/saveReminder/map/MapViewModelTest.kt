package com.udacity.project4.ui.saveReminder.map

import com.example.domain.entity.Address
import com.example.domain.entity.Coordinates
import com.example.domain.use_case.GetAddressInfoUseCase
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.ui.TestDispatcherProvider
import com.udacity.project4.ui.repository.FakeGeofenceReminderRepository
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MapViewModelTest {
    private val testDispatcherProvider = TestDispatcherProvider()
    private val testScope = TestScope(testDispatcherProvider.testDispatcher)
    lateinit var mapViewModel: MapViewModel
    lateinit var fakeGeofenceReminderRepository: FakeGeofenceReminderRepository

    @BeforeEach
    fun setUp() {
        fakeGeofenceReminderRepository = FakeGeofenceReminderRepository()
        val getAddressInfoUseCase = GetAddressInfoUseCase(fakeGeofenceReminderRepository)
        mapViewModel = MapViewModel(getAddressInfoUseCase,testDispatcherProvider)
    }

    @Test
    fun `onSearch() with location name then update addressesState`() = testScope.runTest {
        mapViewModel.onSearch("marsa matrouh")

        assertThat(mapViewModel.addressesState.value).isEqualTo(emptyList<List<Address>>())

        testDispatcherProvider.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(mapViewModel.addressesState.value).isEqualTo(fakeGeofenceReminderRepository.addressesList)
    }

    @Test
    fun `onSearch() with network error then update errorMessage`() = testScope.runTest {
        fakeGeofenceReminderRepository.shouldReturnNetworkErrors = true

        mapViewModel.onSearch("marsa matrouh")

        assertThat(mapViewModel.errorMessage.value).isNull()

        testDispatcherProvider.testDispatcher.scheduler.advanceUntilIdle()
        assertThat(mapViewModel.addressesState.value).isEqualTo(emptyList<List<Address>>())
        assertThat(mapViewModel.errorMessage.value).isNotNull()
    }


    @Test
    fun `onMapClick() with coordinates then update coordinates and addressState`() = testScope.runTest {
        assertThat(mapViewModel.currentCoordinates.value).isNull()

        mapViewModel.onMapClick(Coordinates(1.0,1.0))

        testDispatcherProvider.testDispatcher.scheduler.advanceUntilIdle()

        assertThat(mapViewModel.currentCoordinates.value).isEqualTo(Coordinates(1.0,1.0))
        assertThat(mapViewModel.addressState.value).isEqualTo(fakeGeofenceReminderRepository.addressesList.first())
        assertThat(mapViewModel.errorMessage.value).isNull()
    }

    @Test
    fun `onMapClick() with network error then update errorMessage`() = testScope.runTest {
        fakeGeofenceReminderRepository.shouldReturnNetworkErrors = true
        assertThat(mapViewModel.currentCoordinates.value).isNull()

        mapViewModel.onMapClick(Coordinates(1.0,1.0))

        testDispatcherProvider.testDispatcher.scheduler.advanceUntilIdle()

        assertThat(mapViewModel.currentCoordinates.value).isEqualTo(Coordinates(1.0,1.0))
        assertThat(mapViewModel.addressState.value).isNull()
        assertThat(mapViewModel.errorMessage.value).isNotNull()
    }
}