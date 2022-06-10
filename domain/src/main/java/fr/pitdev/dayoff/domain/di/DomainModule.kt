package fr.pitdev.dayoff.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import fr.pitdev.dayoff.domain.repository.DayOffRepository
import fr.pitdev.dayoff.domain.usecases.dayoffs.GetDayOffsUseCase

@Module
@InstallIn(ViewModelComponent::class)
object DomainModule {

    @Provides
    fun providesGetDayOffUseCase(dayOffRepository: DayOffRepository): GetDayOffsUseCase =
        GetDayOffsUseCase(dayOffRepository)
}