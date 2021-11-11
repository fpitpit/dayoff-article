package fr.pitdev.dayoff.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.pitdev.dayoff.data.room.entities.DayOffDao
import fr.pitdev.dayoff.data.room.entities.DayOffDatabase
import fr.pitdev.dayoff.data.room.entities.DayOffDatabase.Companion.DATABASE_NAME
import javax.inject.Qualifier
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideDayOffDatabase(@ApplicationContext context: Context): DayOffDatabase =
        Room.databaseBuilder(context, DayOffDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    fun provideDayOffDao(dayOffDatabase: DayOffDatabase): DayOffDao =
        dayOffDatabase.dayoffDao()

}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InMemoryDatabase

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class InFileDatabase

