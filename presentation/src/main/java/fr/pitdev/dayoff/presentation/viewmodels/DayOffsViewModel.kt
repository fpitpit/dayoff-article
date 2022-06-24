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
import fr.pitdev.dayoff.presentation.fragments.DayOffsFragmentArgs
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

    private val arg = DayOffsFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val _uiState: MutableStateFlow<DayfOffsState> = MutableStateFlow(DayfOffsState.Loading)
    val uiState: StateFlow<DayfOffsState> = _uiState

    init {
        getDayOffs(arg.param)
    }

    fun refresh() {
        viewModelScope.launch(coroutineDispatcherProvider.default) {
            getDayOffs(arg.param, refresh = true)
        }
    }

    private fun getDayOffs(
        param: DayOffViewModelParam? = DayOffViewModelParam(),
        refresh: Boolean = false
    ) {

        viewModelScope.launch(coroutineDispatcherProvider.default) {
            getDayOffsUseCase(param?.zone, param?.year, refresh = refresh).collect { result ->
                when (result) {
                    is NetworkStatus.Loading -> _uiState.tryEmit(DayfOffsState.Loading)
                    is NetworkStatus.Success -> _uiState.tryEmit(DayfOffsState.Loaded(dayOffs = result.data))
                    is NetworkStatus.Error -> _uiState.tryEmit(
                        DayfOffsState.Error(
                            throwable = result.throwable,
                            message = result.errorMessage
                        )
                    )
                    is NetworkStatus.Exception -> _uiState.tryEmit(
                        DayfOffsState.Error(
                            throwable = result.dayOffException.cause,
                            message = result.dayOffException.message
                        )
                    )

                }
            }
        }
    }
}

sealed class DayfOffsState : Parcelable {
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

