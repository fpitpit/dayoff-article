package fr.pitdev.dayoff.data.dtos

import androidx.annotation.Keep
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.models.Zone
import java.time.LocalDate
import java.time.format.DateTimeFormatter


enum class ZoneDto(name: String) {
    ALSACE_MOSELLE("alsace-moselle"),
    GUADELOUPE(name = "guadelooupe"),
    GUYANE("guyane"),
    LA_REUNION(name = "la-reunion"),
    MARTINIQUE(name = "martinique"),
    MAYOTTE("mayotte"),
    METROPOLE("metropole"),
    NOUVELLE_CALEDONIE("nouvelle-caledonie"),
    POLYNESIE_FRANCAISE("polynesie-francaise"),
    SAINT_BARTHELEMY(name = "saint-barthelemy"),
    SAINT_MARTIN("saint-martin"),
    SAINT_PIERRE_ET_MIQUELON("saint-pierre-et-miquelon"),
    WALLIS_ET_FUTUNA("wallis-et-futuna"),
}

@Keep
data class DayOffDto(val dates: Map<String, String>)

fun ZoneDto.toDomain(): Zone = Zone.valueOf(name)

fun DayOffDto.toDomain(zoneDto: ZoneDto): List<DayOff> {
    return dates.map {
        DayOff(
            zone = zoneDto.toDomain(),
            LocalDate.parse(it.key, DateTimeFormatter.BASIC_ISO_DATE),
            it.value
        )
    }
}

fun Zone.toDto(): ZoneDto = ZoneDto.valueOf(name)