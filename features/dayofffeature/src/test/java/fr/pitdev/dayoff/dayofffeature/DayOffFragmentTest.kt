package fr.pitdev.dayoff.dayofffeature

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class DayOffFragmentTest : HiltTest() {

    @Test
    @Ignore("for instant")
    fun test() = runTest {

    }

}