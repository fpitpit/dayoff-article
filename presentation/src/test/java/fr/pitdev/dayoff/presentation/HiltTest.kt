package fr.pitdev.dayoff.presentation

import androidx.annotation.CallSuper
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@HiltAndroidTest
@Config(application = HiltTestApplication::class, sdk = [Config.OLDEST_SDK, Config.TARGET_SDK], manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
abstract class HiltTest {

    @get:Rule
    var hiltRule: HiltAndroidRule = HiltAndroidRule(this)


    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        hiltRule.inject()

    }
}