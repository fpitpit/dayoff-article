package fr.pitdev.dayoff.domain.usecases.dayoffs

import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.models.Zone
import fr.pitdev.dayoff.domain.repository.DayOffRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate


class GetDayOffsUseCaseTest {
    private lateinit var getDayOffsUseCase: GetDayOffsUseCase
    private lateinit var dayOffRepository: DayOffRepository

    @Before
    fun setUp() {
        dayOffRepository = mockk(relaxed = true)
        getDayOffsUseCase = GetDayOffsUseCase(dayOffRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN dayoff data WHEN get dayoff and year for zone THEN return list of dayoff`() =
        runTest {
            val expected = listOf(
                DayOff(
                    id = 1,
                    zone = Zone.METROPOLE,
                    date = LocalDate.parse("2021-11-11"),
                    name = "Armistice"
                )
            )
            coEvery { dayOffRepository.getDayOffs(any(), any()) } returns flow {
                emit(NetworkStatus.Loading)
                emit(NetworkStatus.Success(expected))
            }
            val result = getDayOffsUseCase.invoke(year = 2021).toList()
            assertTrue(result.contains(NetworkStatus.Loading))
            assertTrue(result.contains(NetworkStatus.Success(expected)))
        }
}