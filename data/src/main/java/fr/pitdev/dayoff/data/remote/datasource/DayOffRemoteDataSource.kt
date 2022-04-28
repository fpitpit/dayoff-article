package fr.pitdev.dayoff.data.remote.datasource

import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.data.dtos.DayOffDto
import fr.pitdev.dayoff.data.dtos.ZoneDto


interface DayOffRemoteDataSource {

    suspend fun getAll(zoneDto: ZoneDto): NetworkStatus<DayOffDto>
    suspend fun getAll(zoneDto: ZoneDto, year: Int): NetworkStatus<DayOffDto>

}