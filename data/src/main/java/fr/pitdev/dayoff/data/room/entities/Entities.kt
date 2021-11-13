package fr.pitdev.dayoff.data.room.entities

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.models.Zone
import java.time.LocalDate

@Entity(
    tableName = "day_off",
    indices = [
        Index(
            name = "day_off_CHK1",
            value = ["day_off_year", "day_off_zone", "day_off_date"],
            unique = true
        )]
)
data class DayOffEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @NonNull @ColumnInfo(name = "day_off_year") val year: Int,
    @NonNull @ColumnInfo(name = "day_off_zone") val zone: Zone,
    @NonNull @ColumnInfo(name = "day_off_date") val dateDayOff: LocalDate,
    @NonNull @ColumnInfo(name = "day_off_name") val name: String
)

fun DayOff.toEntity(): DayOffEntity {
    return DayOffEntity(
        year = this.date.year,
        zone = this.zone,
        dateDayOff = this.date,
        name = this.name
    )
}

fun DayOffEntity.toDomain(): DayOff {
    return DayOff(id= id, zone = this.zone, date = this.dateDayOff, name = this.name)
}