package fr.pitdev.dayoff.presentation.viewmodels


import androidx.test.ext.junit.runners.AndroidJUnit4
import fr.pitdev.dayoff.common.utils.network.NetworkStatus
import fr.pitdev.dayoff.domain.usecases.dayoffs.GetDayOffsUseCase
import fr.pitdev.dayoff.presentation.BaseTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DayOffsViewModelTest : BaseTest() {

    lateinit var dayOffsViewModel: DayOffsViewModel
    var getDayOffsUseCase: GetDayOffsUseCase = mockk()

    @Before
    fun setUp() {
        dayOffsViewModel = DayOffsViewModel(getDayOffsUseCase)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testViewModel() = testRule.runBlockingTest {

        coEvery {
            getDayOffsUseCase.invoke(
                any(),
                any()
            )
        } returns flow { emit(NetworkStatus.Loading) }
        val result = dayOffsViewModel.getDayOffs().toList()
        assertTrue(result.contains(NetworkStatus.Loading))
    }
}
