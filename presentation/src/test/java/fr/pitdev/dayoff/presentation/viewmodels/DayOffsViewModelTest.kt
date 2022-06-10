package fr.pitdev.dayoff.presentation.viewmodels


import dagger.hilt.android.testing.HiltTestApplication
import fr.pitdev.dayoff.common.coroutines.TestCoroutineDispatcherProvider
import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.usecases.dayoffs.GetDayOffsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toSet
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

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
    fun testViewModel() = runTest {
        val testCoroutineDispatcher = StandardTestDispatcher()
        coEvery {
            getDayOffsUseCase.invoke(
                any(),
                any()
            )
        } returns flowOf(NetworkStatus.Loading, NetworkStatus.Success(emptyList()))
        dayOffsViewModel =
            DayOffsViewModel(
                coroutineDispatcherProvider,
                getDayOffsUseCase,

                )
        val sequence = mutableSetOf<NetworkStatus<List<DayOff>>>()
        dayOffsViewModel.getDayOffs().toSet(sequence)
        val expected: List<NetworkStatus<List<DayOff>>> =
            listOf(NetworkStatus.Loading, NetworkStatus.Success(emptyList()))
        assertTrue(sequence.containsAll(expected))
    }
}
