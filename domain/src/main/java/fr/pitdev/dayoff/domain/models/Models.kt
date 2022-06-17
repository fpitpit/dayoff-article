package fr.pitdev.dayoff.domain.models

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Keep
enum class Zone(val zoneName: String) {
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

@Parcelize
@Keep
data class DayOff(val id: Int? = null, val zone: Zone, val date: LocalDate, val name: String) :
    Parcelable



