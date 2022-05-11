package fr.pitdev.dayoff.presentation.viewmodels


import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.usecases.dayoffs.GetDayOffsUseCase
import fr.pitdev.dayoff.presentation.HiltTest
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verifySequence
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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
                getDayOffsUseCase
            )
        val result: List<NetworkStatus<List<DayOff>>> = dayOffsViewModel.getDayOffs().toList()
        val expected: List<NetworkStatus<List<DayOff>>> = listOf(NetworkStatus.Loading, NetworkStatus.Success(emptyList()))
        assertTrue(result.containsAll(expected))
    }
}
