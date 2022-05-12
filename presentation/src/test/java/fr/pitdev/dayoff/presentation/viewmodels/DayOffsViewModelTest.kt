package fr.pitdev.dayoff.presentation.viewmodels


import androidx.test.ext.junit.runners.AndroidJUnit4
import fr.pitdev.dayoff.common.coroutines.CoroutineDispatcherProvider
import fr.pitdev.dayoff.common.coroutines.TestCoroutineDispatcherProvider
import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.usecases.dayoffs.GetDayOffsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DayOffsViewModelTest {

    private lateinit var dayOffsViewModel: DayOffsViewModel

    private val getDayOffsUseCase: GetDayOffsUseCase = mockk()


    @ExperimentalCoroutinesApi
    @Test
    fun testViewModel() = runTest {
        coEvery {
            getDayOffsUseCase.invoke(
                any(),
                any()
            )
        } returns flowOf(NetworkStatus.Loading, NetworkStatus.Success(emptyList()))
        dayOffsViewModel =
            DayOffsViewModel(
                TestCoroutineDispatcherProvider(),
                getDayOffsUseCase,

                )
        val sequence = mutableSetOf<NetworkStatus<List<DayOff>>>()
        dayOffsViewModel.getDayOffs().toSet(sequence)
        val expected: List<NetworkStatus<List<DayOff>>> =
            listOf(NetworkStatus.Loading, NetworkStatus.Success(emptyList()))
        assertTrue(sequence.containsAll(expected))
    }
}
