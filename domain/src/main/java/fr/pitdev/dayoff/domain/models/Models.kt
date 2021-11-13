package fr.pitdev.dayoff.domain.models

import androidx.annotation.Keep
import java.time.LocalDate

@Keep
enum class Zone(zoneName: String) {
    ALSACE_MOSELLE("alsace-moselle"),
    GUADELOUPE("guadelooupe"),
    GUYANE("guyane"),
    LA_REUNION("la-reunion"),
    MARTINIQUE("martinique"),
    MAYOTTE("mayotte"),
    METROPOLE("metropole"),
    NOUVELLE_CALEDONIE("nouvelle-caledonie"),
    POLYNESIE_FRANCAISE("polynesie-francaise"),
    SAINT_BARTHELEMY("saint-barthelemy"),
    SAINT_MARTIN("saint-martin"),
    SAINT_PIERRE_ET_MIQUELON("saint-pierre-et-miquelon"),
    WALLIS_ET_FUTUNA("wallis-et-futuna"),
}

@Keep
data class DayOff(val id: Int? = null, val zone: Zone, val date: LocalDate, val name: String)



