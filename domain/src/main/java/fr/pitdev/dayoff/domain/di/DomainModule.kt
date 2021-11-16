package fr.pitdev.dayoff.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.pitdev.dayoff.domain.coroutines.DefaultDispatcherProvider
import fr.pitdev.dayoff.domain.coroutines.DispatchProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Singleton
    internal fun provideDispatcherProvider(): DispatchProvider = DefaultDispatcherProvider()
}