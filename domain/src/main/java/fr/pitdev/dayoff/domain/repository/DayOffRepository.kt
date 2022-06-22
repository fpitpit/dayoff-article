package fr.pitdev.dayoff.domain.repository

import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.models.Zone
import kotlinx.coroutines.flow.Flow


interface DayOffRepository {

    fun getDayOffs(zone: Zone, year: Int, refresh: Boolean = false): Flow<NetworkStatus<List<DayOff>>>
}