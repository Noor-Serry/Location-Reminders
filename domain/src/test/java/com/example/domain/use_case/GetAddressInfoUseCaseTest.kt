package com.example.domain.use_case

import com.example.domain.entity.Address
import com.example.domain.entity.Coordinates
import com.example.domain.entity.Error
import com.example.domain.repository.FakeGeofenceReminderRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetAddressInfoUseCaseTest {


    private lateinit var getAddressInfoUseCase: GetAddressInfoUseCase
    private val fakeReminderRepository = FakeGeofenceReminderRepository()

    @BeforeEach
    fun setUp() {
        getAddressInfoUseCase = GetAddressInfoUseCase(fakeReminderRepository)
    }

    @Test
    fun `getFirstAddress() with empty addresses List then return null`() {
        val addresses = emptyList<Address>()

        val result = getAddressInfoUseCase.getFirstAddress(addresses)

        assertThat(result).isNull()
    }

    @Test
    fun `getFirstAddress() with non empty addresses List then return first address`() {
        val address1 = Address("", "", "", Coordinates(1.0, 1.0))
        val address2 = Address("", "", "", Coordinates(1.0, 1.0))
        val addresses = listOf(address1, address2)

        val result = getAddressInfoUseCase.getFirstAddress(addresses)

        assertThat(result).isEqualTo(address1)
    }

    @Test
    fun `getAddressInfoByAddressName() with out network error then return list of Address`() = runTest {
        val cityName = "marsa matrouh"


        val result = getAddressInfoUseCase.getAddressInfo(cityName).toList()

        assertThat(result).isNotNull()

    }

    @Test
    fun `getAddressInfoByAddressName() with network error then throw Exception`() = runTest {

        fakeReminderRepository.shouldReturnNetworkErrors = true
        val cityName = "marsa matrouh"

        assertThrows<Error.AddressError>{
            getAddressInfoUseCase.getAddressInfo(cityName)
        }

    }

    @Test
    fun `getAddressInfoByCoordinate() with network error then throw Exception`() = runTest {

        fakeReminderRepository.shouldReturnNetworkErrors = true
        val coordinates = Coordinates(1.0, 1.0)

        assertThrows<Error.AddressError>{
            getAddressInfoUseCase.getAddressInfo(coordinates).toList()
        }
    }

    @Test
    fun `getAddressInfoByCoordinates() with out network error then return list of Address`() = runTest {
        val coordinates = Coordinates(1.0, 1.0)

        val result = getAddressInfoUseCase.getAddressInfo(coordinates).toList()

        assertThat(result).isNotNull()
    }

}