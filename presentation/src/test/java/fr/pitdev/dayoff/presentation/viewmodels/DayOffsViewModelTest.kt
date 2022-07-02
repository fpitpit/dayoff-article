package fr.pitdev.dayoff.presentation.viewmodels


import dagger.hilt.android.testing.HiltTestApplication
import fr.pitdev.dayoff.common.coroutines.TestCoroutineDispatcherProvider
import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.domain.models.Zone
import fr.pitdev.dayoff.domain.usecases.dayoffs.GetDayOffsUseCase
import fr.pitdev.dayoff.presentation.fragments.DayOffsFragmentArgs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.time.LocalDate

@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class, sdk = [Config.OLDEST_SDK, Config.TARGET_SDK])
class DayOffsViewModelTest {

    private lateinit var coroutineDispatcherProvider: TestCoroutineDispatcherProvider
    private lateinit var dayOffsViewModel: DayOffsViewModel

    private val getDayOffsUseCase: GetDayOffsUseCase = mockk()

    @Before
    fun setUo() {
        coroutineDispatcherProvider = TestCoroutineDispatcherProvider()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testViewModel() = runTest(UnconfinedTestDispatcher()) {

        coEvery {
            getDayOffsUseCase.invoke(
                any(),
                any()
            )
        } returns flowOf(NetworkStatus.Loading, NetworkStatus.Success(emptyList()))


        dayOffsViewModel =
            DayOffsViewModel(
                savedStateHandle = DayOffsFragmentArgs(param = DayOffViewModelParam(zone = Zone.GUYANE))
                    .toSavedStateHandle(),
                coroutineDispatcherProvider,
                getDayOffsUseCase
            )

        val sequence = mutableSetOf<DayfOffsState>()
        val job = launch {
            dayOffsViewModel.uiStateAsFlow.toSet(sequence)
        }
        val expected: List<DayfOffsState> =
            listOf(DayfOffsState.Loaded(emptyList()))
        assertTrue(sequence.containsAll(expected))
        job.cancel()
        coVerify {
            getDayOffsUseCase(Zone.GUYANE, LocalDate.now().year)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testViewModelError() = runTest(UnconfinedTestDispatcher()) {
        val error = IllegalArgumentException()
        coEvery {
            getDayOffsUseCase.invoke(
                any(),
                any()
            )
        } returns flowOf(NetworkStatus.Loading, NetworkStatus.Error(throwable = error))


        dayOffsViewModel =
            DayOffsViewModel(
                savedStateHandle = DayOffsFragmentArgs(param = DayOffViewModelParam(zone = Zone.GUYANE))
                    .toSavedStateHandle(),
                coroutineDispatcherProvider,
                getDayOffsUseCase
            )

        val sequence = mutableSetOf<DayfOffsState>()
        val job = launch {
            dayOffsViewModel.uiStateAsFlow.toSet(sequence)
        }
        val expected: List<DayfOffsState> =
            listOf(DayfOffsState.Error(error))
        assertTrue(sequence.containsAll(expected))
        job.cancel()
        coVerify {
            getDayOffsUseCase(Zone.GUYANE, LocalDate.now().year)
        }
    }
}
