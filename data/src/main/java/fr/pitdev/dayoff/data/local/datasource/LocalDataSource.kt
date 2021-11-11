package fr.pitdev.dayoff.data.local.datasource

import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.models.Zone
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun saveDayOffs(dayOffs: List<DayOff>)

    fun getAllDayOff(): Flow<List<DayOff>>

    fun getAllDayOff(zone: Zone): Flow<List<DayOff>>

    fun getAllDayOff(zone: Zone, year: Int): Flow<List<DayOff>>
}