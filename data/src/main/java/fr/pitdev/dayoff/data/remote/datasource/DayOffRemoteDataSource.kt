package fr.pitdev.dayoff.data.remote.datasource

import fr.pitdev.dayoff.data.dtos.DayOffDto
import fr.pitdev.dayoff.data.dtos.ZoneDto
import fr.pitdev.dayoff.data.utils.NetworkStatus


interface DayOffRemoteDataSource {

    suspend fun getAll(zone: ZoneDto): NetworkStatus<DayOffDto>
    suspend fun getAll(zoneDto: ZoneDto, year: Int): NetworkStatus<DayOffDto>

}