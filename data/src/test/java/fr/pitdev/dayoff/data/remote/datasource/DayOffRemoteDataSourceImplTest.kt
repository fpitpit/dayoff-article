package fr.pitdev.dayoff.data.remote.datasource

import fr.pitdev.dayoff.common.base.utils.CONNECT_EXCEPTION
import fr.pitdev.dayoff.data.dtos.DayOffDto
import fr.pitdev.dayoff.data.dtos.ZoneDto
import fr.pitdev.dayoff.data.remote.api.DayOffApiService
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.internal.EMPTY_RESPONSE
import org.junit.Test
import retrofit2.Response
import java.net.ConnectException

class DayOffRemoteDataSourceImplTest {
    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN success with dayoff THEN call api THEN return success`() = runBlockingTest {
        val dayOffApiService: DayOffApiService = mockk()

        coEvery { dayOffApiService.getAll(any(), any()) } returns Response.success(
            DayOffDto(
                mapOf("2021-07-14" to "fête nationale")
            )
        )
        val dayOffRemoteDataSourceImpl =
            DayOffRemoteDataSourceImpl(dayOffApiService = dayOffApiService)
        val result = dayOffRemoteDataSourceImpl.getAll(ZoneDto.METROPOLE)
        assertNotNull(result)
        assertEquals("fête nationale", result.data?.dates?.get("2021-07-14"))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN error 500 THEN call api THEN return network error`() = runBlockingTest {
        val dayOffApiService: DayOffApiService = mockk()

        coEvery { dayOffApiService.getAll(any(), any()) } returns Response.error(
            500,
            EMPTY_RESPONSE
        )
        val dayOffRemoteDataSourceImpl =
            DayOffRemoteDataSourceImpl(dayOffApiService = dayOffApiService)
        val result = dayOffRemoteDataSourceImpl.getAll(ZoneDto.METROPOLE)
        assertNotNull(result)
        assertEquals("Response.error()", result.errorMessage)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `GIVEN exception THEN call api THEN return network error connect exception`() =
        runBlockingTest {
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
            assertNotNull(result)
            assertEquals(CONNECT_EXCEPTION, result.errorMessage)
        }
}