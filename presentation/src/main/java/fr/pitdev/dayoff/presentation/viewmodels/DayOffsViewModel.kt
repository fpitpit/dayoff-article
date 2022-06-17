package fr.pitdev.dayoff.presentation.viewmodels

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.pitdev.dayoff.common.coroutines.CoroutineDispatcherProvider
import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.models.Zone
import fr.pitdev.dayoff.domain.usecases.dayoffs.GetDayOffsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class DayOffsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
    private val getDayOffsUseCase: GetDayOffsUseCase
) : ViewModel() {

    private val arg = savedStateHandle.getLiveData(PARAM, DayOffViewModelParam())
    private val _uiState: MutableStateFlow<DayfOffsState> = MutableStateFlow(DayfOffsState.Idle)
    val uiState: StateFlow<DayfOffsState> = _uiState

    init {
        getDayOffs(arg.value)
    }

    private fun getDayOffs(param: DayOffViewModelParam? = DayOffViewModelParam()) {

        viewModelScope.launch(coroutineDispatcherProvider.default) {
            getDayOffsUseCase(param?.zone, param?.year).collect { result ->
                when (result) {
                    is NetworkStatus.Loading -> _uiState.tryEmit(DayfOffsState.Loading)
                    is NetworkStatus.Success -> _uiState.tryEmit(DayfOffsState.Loaded(dayOffs = result.data))
                    is NetworkStatus.Error -> _uiState.tryEmit(
                        DayfOffsState.Error(
                            throwable = result.throwable,
                            message = result.errorMessage
                        )
                    )

                }
            }
        }
    }

    companion object {
        const val PARAM: String = "param"
    }
}

sealed class DayfOffsState : Parcelable {
    @Parcelize
    object Idle : DayfOffsState()

    @Parcelize
    object Loading : DayfOffsState()

    @Parcelize
    data class Loaded(val dayOffs: List<DayOff>) : DayfOffsState()

    @Parcelize
    data class Error(val throwable: Throwable? = null, val message: String? = null) :
        DayfOffsState()

}

@Keep
@Parcelize
data class DayOffViewModelParam(
    val zone: Zone = Zone.METROPOLE,
    val year: Int = LocalDate.now().year
) : Parcelable

