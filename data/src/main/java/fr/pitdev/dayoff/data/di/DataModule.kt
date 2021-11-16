package fr.pitdev.dayoff.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.pitdev.dayoff.data.local.datasource.LocalDataSource
import fr.pitdev.dayoff.data.local.datasource.LocalDataSourceImpl
import fr.pitdev.dayoff.data.remote.api.DayOffApiService
import fr.pitdev.dayoff.data.remote.datasource.DayOffRemoteDataSource
import fr.pitdev.dayoff.data.remote.datasource.DayOffRemoteDataSourceImpl
import fr.pitdev.dayoff.data.repository.DayOffRepositoryImpl
import fr.pitdev.dayoff.data.room.entities.DayOffDao
import fr.pitdev.dayoff.domain.coroutines.DispatchProvider
import fr.pitdev.dayoff.domain.repository.DayOffRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideLocalDataSourceInFile(dayOffDao: DayOffDao): LocalDataSource {
        return LocalDataSourceImpl(dayOffDao)
    }

    @Provides
    @Singleton
    fun provideDayOffRepository(
        dispatchProvider: DispatchProvider,
        remoteDataSource: DayOffRemoteDataSource,
        localDataSource: LocalDataSource
    ): DayOffRepository {
        return DayOffRepositoryImpl(dispatchProvider, remoteDataSource, localDataSource)
    }

}

@InstallIn(SingletonComponent::class)
@Module
object RemoteSourceModule {
    @Provides
    @Singleton
    fun provideRemoteDataSource(apiService: DayOffApiService): DayOffRemoteDataSource {
        return DayOffRemoteDataSourceImpl(apiService)
    }
}

