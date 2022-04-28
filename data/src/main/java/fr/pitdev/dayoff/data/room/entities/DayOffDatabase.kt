package fr.pitdev.dayoff.data.room.entities

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DayOffEntity::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class DayOffDatabase : RoomDatabase() {

    abstract fun dayoffDao(): DayOffDao

    companion object {
        const val DATABASE_NAME = "day_off_db"
    }
}