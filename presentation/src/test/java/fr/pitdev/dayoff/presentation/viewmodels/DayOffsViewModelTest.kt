package fr.pitdev.dayoff.presentation.viewmodels


import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.domain.usecases.dayoffs.GetDayOffsUseCase
import fr.pitdev.dayoff.presentation.HiltTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
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
        } returns flowOf(NetworkStatus.Loading)
        dayOffsViewModel =
            DayOffsViewModel(
                getDayOffsUseCase
            )
        val result = dayOffsViewModel.getDayOffs().toList()
        assertTrue(result.contains(NetworkStatus.Loading))


    }
}
