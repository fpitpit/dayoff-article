package extensions

import android.text.format.DateUtils
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

fun LocalDate.isToday(): Boolean = DateUtils.isToday(
    Date.from(
        atStartOfDay(ZoneId.systemDefault()).toInstant()
    ).time
)