package fr.pitdev.dayoff.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import fr.pitdev.dayoff.data.room.entities.DayOffDao
import fr.pitdev.dayoff.data.room.entities.DayOffDatabase
import javax.inject.Singleton

@TestInstallIn(components = [SingletonComponent::class], replaces = [DatabaseModule::class])
@Module
object DatabaseModuleForTest {
    @Singleton
    @Provides
    fun provideDayOffDatabaseInMemory(@ApplicationContext context: Context): DayOffDatabase =
        Room.inMemoryDatabaseBuilder(context, DayOffDatabase::class.java)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun provideDayOffDaodayOffDatabaseInMemory(dayOffDatabase: DayOffDatabase): DayOffDao =
        dayOffDatabase.dayoffDao()
}