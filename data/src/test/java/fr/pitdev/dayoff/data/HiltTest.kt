package fr.pitdev.dayoff.data

import androidx.annotation.CallSuper
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import fr.pitdev.dayoff.data.di.DatabaseModule
import fr.pitdev.dayoff.data.di.RemoteSourceModule
import fr.pitdev.dayoff.data.remote.datasource.DayOffRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@HiltAndroidTest
@Config(application = HiltTestApplication::class, sdk = [30], manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
abstract class HiltTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @ExperimentalCoroutinesApi
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()


    @ExperimentalCoroutinesApi
    @CallSuper
    @Before
    open fun setUp() {
        hiltRule.inject()

    }

    @After
    @CallSuper
    open fun after() {

    }
}