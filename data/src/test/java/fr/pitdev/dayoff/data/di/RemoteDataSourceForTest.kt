package fr.pitdev.dayoff.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import fr.pitdev.dayoff.data.remote.datasource.DayOffRemoteDataSource
import io.mockk.mockk
import javax.inject.Singleton

@TestInstallIn(components = [SingletonComponent::class], replaces = [RemoteSourceModule::class])
@Module
object RemoteDataSourceModuleForTest {


    @Singleton
    @Provides
    fun provideRemoteDataSource(): DayOffRemoteDataSource = mockk()

}