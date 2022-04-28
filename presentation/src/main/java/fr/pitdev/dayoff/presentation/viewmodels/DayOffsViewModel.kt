package fr.pitdev.dayoff.presentation.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.models.Zone
import fr.pitdev.dayoff.domain.usecases.dayoffs.GetDayOffsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DayOffsViewModel @Inject constructor(
    private val getDayOffsUseCase: GetDayOffsUseCase
) : ViewModel() {

    fun getDayOffs(
        zone: Zone = Zone.METROPOLE,
        year: Int = LocalDate.now().year
    ): Flow<NetworkStatus<List<DayOff>>> =
        flow {
            getDayOffsUseCase(zone, year).collect {
                emit(it)
            }
        }

}

