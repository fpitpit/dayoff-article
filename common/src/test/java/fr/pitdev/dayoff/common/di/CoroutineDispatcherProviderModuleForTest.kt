package fr.pitdev.dayoff.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import fr.pitdev.dayoff.common.coroutines.CoroutineDispatcherProvider
import fr.pitdev.dayoff.common.coroutines.TestCoroutineDispatcherProvider
import javax.inject.Singleton


@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoroutineDispatcherModule::class]
)
@Module
object CoroutineDispatcherProviderModuleForTest {

    @Provides
    @Singleton
    internal fun provideDispatcherProvider(): CoroutineDispatcherProvider =
        TestCoroutineDispatcherProvider()
}