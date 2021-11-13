package fr.pitdev.dayoff.data.remote.datasource

import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.data.dtos.DayOffDto
import fr.pitdev.dayoff.data.dtos.ZoneDto
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.models.Zone


interface DayOffRemoteDataSource {

    suspend fun getAll(zoneDto: ZoneDto): NetworkStatus<DayOffDto>
    suspend fun getAll(zoneDto: ZoneDto, year: Int): NetworkStatus<DayOffDto>

}