package fr.pitdev.dayoff.domain

import org.junit.Rule

open class BaseTest {
    @get:Rule
    val testRule = TestCoroutineRule()


}
