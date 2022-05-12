package fr.pitdev.dayoff.presentation.viewmodels

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.pitdev.dayoff.common.coroutines.CoroutineDispatcherProvider
import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.models.Zone
import fr.pitdev.dayoff.domain.usecases.dayoffs.GetDayOffsUseCase
import kotlinx.coroutines.flow.*
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DayOffsViewModel @Inject constructor(
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val getDayOffsUseCase: GetDayOffsUseCase
) : ViewModel() {

    fun getDayOffs(
        zone: Zone = Zone.METROPOLE,
        year: Int = LocalDate.now().year
    ): Flow<NetworkStatus<List<DayOff>>> = flow {
        emitAll(getDayOffsUseCase(zone, year))
    }.flowOn(coroutineDispatcherProvider.default)
}

