package fr.pitdev.dayoff.presentation.fragments

import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import fr.pitdev.dayoff.domain.models.DayOff
import fr.pitdev.dayoff.domain.models.Zone
import fr.pitdev.dayoff.presentation.HiltTestActivity
import fr.pitdev.dayoff.presentation.launchFragmentInHiltContainer
import fr.pitdev.dayoff.presentation.viewmodels.DayOffViewModelParam
import fr.pitdev.dayoff.presentation.viewmodels.DayOffsViewModel
import fr.pitdev.dayoff.presentation.viewmodels.DayfOffsState
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate


@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DayOffsFragmentTest {


    @BindValue
    @JvmField
    var dayOffsViewModel: DayOffsViewModel = mockk(relaxed = true)

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        val list =
            listOf(DayOff(zone = Zone.METROPOLE, id = 1, date = LocalDate.now(), name = "TEST"))
        val mutableStateFlow: MutableStateFlow<DayfOffsState> =
            MutableStateFlow(DayfOffsState.Loaded(list))
        every { dayOffsViewModel.uiState } returns mutableStateFlow.asStateFlow()
        hiltRule.inject()
    }


    @Test
    fun testData() {


        launchFragmentInHiltContainer<DayOffsFragment>(
            fragmentArgs = DayOffsFragmentArgs(param = DayOffViewModelParam()).toBundle()
        ) {

        }

    }

    private fun launchActivity(): ActivityScenario<HiltTestActivity>? {
        return launch(HiltTestActivity::class.java)
    }
}