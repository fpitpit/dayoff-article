package fr.pitdev.dayoff.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.pitdev.dayoff.data.coroutines.DefaultDispatcherProvider
import fr.pitdev.dayoff.data.coroutines.DispatchProvider
import fr.pitdev.dayoff.data.local.datasource.LocalDataSource
import fr.pitdev.dayoff.data.local.datasource.LocalDataSourceImpl
import fr.pitdev.dayoff.data.remote.api.DayOffApiService
import fr.pitdev.dayoff.data.remote.datasource.DayOffRemoteDataSource
import fr.pitdev.dayoff.data.remote.datasource.DayOffRemoteDataSourceImpl
import fr.pitdev.dayoff.data.room.entities.DayOffDao

@InstallIn(SingletonComponent::class)
@Module
object DataModule {
    @Provides
    internal fun provideDispatcherProvider(): DispatchProvider = DefaultDispatcherProvider()

    @Provides
    fun provideRemoteDataSource(apiService: DayOffApiService): DayOffRemoteDataSource {
        return DayOffRemoteDataSourceImpl(apiService)
    }

    @Provides
    fun provideLocalDataSourceInFile(dayOffDao: DayOffDao): LocalDataSource {
        return LocalDataSourceImpl(dayOffDao)
    }

}