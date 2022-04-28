package fr.pitdev.dayoff.data.repository

import dagger.hilt.android.testing.HiltAndroidTest
import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.data.HiltTest
import fr.pitdev.dayoff.data.dtos.DayOffDto
import fr.pitdev.dayoff.data.remote.datasource.DayOffRemoteDataSource
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.models.Zone
import fr.pitdev.dayoff.domain.repository.DayOffRepository
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class DayOffRepositoryImplTest : HiltTest() {


    @Inject
    lateinit var dayOffRemoteDataSource: DayOffRemoteDataSource

    @Inject
    lateinit var repositoryImpl: DayOffRepository

    @ExperimentalCoroutinesApi
    override fun setUp() {
        super.setUp()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test() = runTest {
        val dayOffDto = DayOffDto(dates = mapOf("2021-11-11" to "Armistice"))

        coEvery { dayOffRemoteDataSource.getAll(any(), any()) } returns NetworkStatus.Success(
            dayOffDto
        )
        val result: Flow<NetworkStatus<List<DayOff>>> =
            repositoryImpl.getDayOffs(zone = Zone.METROPOLE, 2021)
        val liste: List<NetworkStatus<List<DayOff>>> = result.toList()
        assertTrue(liste.size == 2)
    }
}