package fr.pitdev.dayoff.data.local.datasource

import fr.pitdev.dayoff.data.room.entities.DayOffDao
import fr.pitdev.dayoff.data.room.entities.toDomain
import fr.pitdev.dayoff.data.room.entities.toEntity
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.models.Zone
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl (val dayOffDao: DayOffDao) : LocalDataSource {
    override suspend fun saveDayOffs(dayOffs: List<DayOff>) =
        dayOffDao.save(dayOffs.map { it.toEntity() })

    override fun getAllDayOff(): Flow<List<DayOff>> =
        dayOffDao.getAll().map { value -> value.map { it.toDomain() } }

    override fun getAllDayOff(zone: Zone): Flow<List<DayOff>> =
        dayOffDao.getAllByZone(zone).map { value -> value.map { it.toDomain() } }

    override fun getAllDayOff(zone: Zone, year: Int): Flow<List<DayOff>> =
        dayOffDao.getAllByZoneAndYear(zone, year).map { value -> value.map { it.toDomain() } }


}