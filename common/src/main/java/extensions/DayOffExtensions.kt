package extensions

import android.content.Context
import android.text.format.DateUtils
import android.util.TypedValue
import androidx.annotation.ColorRes
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

fun LocalDate.isToday(): Boolean = DateUtils.isToday(
    Date.from(
        atStartOfDay(ZoneId.systemDefault()).toInstant()
    ).time
)

fun Context.getThemeColor(@ColorRes colorResId: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(
        colorResId,
        typedValue,
        true
    )
    return typedValue.data
}