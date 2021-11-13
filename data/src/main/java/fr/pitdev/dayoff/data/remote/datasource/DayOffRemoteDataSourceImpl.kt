package fr.pitdev.dayoff.data.remote.datasource

import com.google.gson.Gson
import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.data.dtos.DayOffDto
import fr.pitdev.dayoff.data.dtos.ZoneDto
import fr.pitdev.dayoff.data.remote.api.DayOffApiService
import fr.pitdev.dayoff.data.utils.safeApiCall
import retrofit2.Response

class DayOffRemoteDataSourceImpl(private val dayOffApiService: DayOffApiService) :
    DayOffRemoteDataSource {
    override suspend fun getAll(zoneDto: ZoneDto): NetworkStatus<DayOffDto> = safeApiCall {
        parseResponse(dayOffApiService.getAll(zoneDto.zoneName))
    }

    override suspend fun getAll(zoneDto: ZoneDto, year: Int): NetworkStatus<DayOffDto> =
        safeApiCall {
            val response = dayOffApiService.getAll(zoneDto.zoneName, year)
            parseResponse(response)
        }

    private fun parseResponse(response: Response<String>) =
        if (response.isSuccessful) {
            parseResponseSuccess(response)
        } else Response.error(response.code(), response.errorBody()!!)

    private fun parseResponseSuccess(response: Response<String>): Response<DayOffDto> {
        val parsing: HashMap<*, *> = Gson().fromJson(response.body(), HashMap::class.java)
        val dates =
            parsing.map { entry -> Pair(entry.key as String, entry.value as String) }.toMap()
        return Response.success(DayOffDto(dates = dates))
    }
}