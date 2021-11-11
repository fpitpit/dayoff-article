package fr.pitdev.dayoff.data.room.entities

import androidx.room.TypeConverter
import java.time.LocalDate

class Converters {

    @TypeConverter
    fun fromDateToString(date: LocalDate): String {
        return date.toString()
    }

    @TypeConverter
    fun fromStringToLocalDate(date: String): LocalDate {
        return LocalDate.parse(date)
    }
}