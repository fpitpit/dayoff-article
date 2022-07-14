package fr.pitdev.dayoff.data.room.entities

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import fr.pitdev.dayoff.domain.models.Zone
import kotlinx.coroutines.flow.Flow

@Dao
interface DayOffDao {
    @Query("SELECT * FROM day_off ORDER BY day_off_date")
    fun getAll(): Flow<List<DayOffEntity>>

    @Query("SELECT * FROM day_off WHERE day_off_zone = :zone ORDER BY day_off_date")
    fun getAllByZone(zone: Zone): Flow<List<DayOffEntity>>

    @Query("SELECT * FROM day_off WHERE day_off_zone = :zone AND day_off_year = :year ORDER BY day_off_date")
    fun getAllByZoneAndYear(zone: Zone, year: Int): Flow<List<DayOffEntity>>

    @Insert(onConflict = IGNORE)
    fun save(data: List<DayOffEntity>)

    @Delete
    fun delete(vararg data: DayOffEntity)

}
