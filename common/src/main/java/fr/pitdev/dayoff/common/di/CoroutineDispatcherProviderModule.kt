package fr.pitdev.dayoff.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.pitdev.dayoff.common.coroutines.CoroutineDispatcherProvider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatcherModule {

    @Provides
    @Singleton
    internal fun provideDispatcherProvider(): CoroutineDispatcherProvider =
        CoroutineDispatcherProvider()
}