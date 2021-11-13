package fr.pitdev.dayoff.domain.repository

import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.models.Zone
import kotlinx.coroutines.flow.Flow
import java.time.Year

interface DayOffRepository {

    suspend fun getDayOffs(zone: Zone, year: Int): Flow<NetworkStatus<List<DayOff>>>
}