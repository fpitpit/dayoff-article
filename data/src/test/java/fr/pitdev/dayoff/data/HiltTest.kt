package fr.pitdev.dayoff.data

import androidx.annotation.CallSuper
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import fr.pitdev.dayoff.data.room.entities.DayOffDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode
import javax.inject.Inject

@HiltAndroidTest
@Config(application = HiltTestApplication::class, sdk = [Config.OLDEST_SDK, 30, Config.TARGET_SDK], manifest = Config.NONE)
@RunWith(RobolectricTestRunner::class)
@LooperMode(LooperMode.Mode.PAUSED)
abstract class HiltTest {

    @Inject
    lateinit var database: DayOffDatabase

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @ExperimentalCoroutinesApi
    @CallSuper
    @Before
    open fun setUp() {
        hiltRule.inject()

    }

    @After
    @CallSuper
    open fun after() {
        database.close()

    }
}