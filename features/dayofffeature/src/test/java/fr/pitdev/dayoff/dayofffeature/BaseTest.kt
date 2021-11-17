package fr.pitdev.dayoff.dayofffeature

import androidx.annotation.CallSuper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule


abstract class BaseTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val testRule = TestCoroutineRule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
}
