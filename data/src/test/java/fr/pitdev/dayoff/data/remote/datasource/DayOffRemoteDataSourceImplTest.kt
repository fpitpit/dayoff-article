package fr.pitdev.dayoff.data.remote.datasource

import fr.pitdev.dayoff.common.base.utils.CONNECT_EXCEPTION
import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.data.dtos.ZoneDto
import fr.pitdev.dayoff.data.remote.api.DayOffApiService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import okhttp3.internal.EMPTY_RESPONSE
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import retrofit2.Response
import java.net.ConnectException

class DayOffRemoteDataSourceImplTest {
    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN success with dayoff THEN call api THEN return success`() = runTest {
        val dayOffApiService: DayOffApiService = mockk()

        coEvery { dayOffApiService.getAll(any(), any()) } returns Response.success(
            "{" +
                    "  \"2021-01-01\": \"1er janvier\",\n" +
                    "  \"2021-04-05\": \"Lundi de Pâques\",\n" +
                    "  \"2021-05-01\": \"1er mai\",\n" +
                    "  \"2021-05-08\": \"8 mai\",\n" +
                    "  \"2021-05-13\": \"Ascension\",\n" +
                    "  \"2021-05-24\": \"Lundi de Pentecôte\",\n" +
                    "  \"2021-07-14\": \"14 juillet\",\n" +
                    "  \"2021-08-15\": \"Assomption\",\n" +
                    "  \"2021-11-01\": \"Toussaint\",\n" +
                    "  \"2021-11-11\": \"11 novembre\",\n" +
                    "  \"2021-12-25\": \"Jour de Noël\"\n" +
                    "}"
        )

        val dayOffRemoteDataSourceImpl =
            DayOffRemoteDataSourceImpl(dayOffApiService = dayOffApiService)
        val result = dayOffRemoteDataSourceImpl.getAll(ZoneDto.METROPOLE)
        assertNotNull(result as NetworkStatus.Success)

        assertEquals("14 juillet", result.data.dates["2021-07-14"])
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN error 500 THEN call api THEN return network error`() = runTest {
        val dayOffApiService: DayOffApiService = mockk()

        coEvery { dayOffApiService.getAll(any(), any()) } returns Response.error(
            500,
            EMPTY_RESPONSE
        )
        val dayOffRemoteDataSourceImpl =
            DayOffRemoteDataSourceImpl(dayOffApiService = dayOffApiService)
        val result = dayOffRemoteDataSourceImpl.getAll(ZoneDto.METROPOLE)
        assertNotNull(result as NetworkStatus.Error)
        assertEquals("Response.error()", result.errorMessage)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN exception THEN call api THEN return network error connect exception`() =
        runTest {
            val dayOffApiService: DayOffApiService = mockk()

            coEvery {
                dayOffApiService.getAll(
                    any(),
                    any()
                )
            } throws ConnectException("ConnectException")
            val dayOffRemoteDataSourceImpl =
                DayOffRemoteDataSourceImpl(dayOffApiService = dayOffApiService)
            val result = dayOffRemoteDataSourceImpl.getAll(ZoneDto.METROPOLE)
            assertNotNull(result as NetworkStatus.Error)
            assertEquals(CONNECT_EXCEPTION, result.errorMessage)
        }
}