package fr.pitdev.dayoff.data.remote.datasource

import fr.pitdev.dayoff.data.dtos.DayOffDto
import fr.pitdev.dayoff.data.dtos.ZoneDto
import fr.pitdev.dayoff.data.remote.api.DayOffApiService
import fr.pitdev.dayoff.data.utils.NetworkStatus
import fr.pitdev.dayoff.data.utils.safeApiCall

class DayOffRemoteDataSourceImpl(private val dayOffApiService: DayOffApiService) :
    DayOffRemoteDataSource {
    override suspend fun getAll(zone: ZoneDto): NetworkStatus<DayOffDto> = safeApiCall {
        dayOffApiService.getAll(zone.name)
    }

    override suspend fun getAll(zone: ZoneDto, year: Int): NetworkStatus<DayOffDto> = safeApiCall {
        dayOffApiService.getAll(zone.name, year)
    }

}