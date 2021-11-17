package fr.pitdev.dayoff.common.coroutines

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

open class CoroutineDispatcherProvider {
    open val main: MainCoroutineDispatcher by lazy { Dispatchers.Main.immediate }
    open val io: CoroutineDispatcher by lazy { Dispatchers.IO }
    open val default: CoroutineDispatcher by lazy { Dispatchers.Default }
    open val unconfined: CoroutineDispatcher by lazy { Dispatchers.Unconfined }
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
class TestCoroutineDispatcherProvider : CoroutineDispatcherProvider() {
    override val main: MainCoroutineDispatcher by lazy { Dispatchers.Main }
    override val io: CoroutineDispatcher by lazy { Dispatchers.Main }
    override val default: CoroutineDispatcher by lazy { Dispatchers.Main }
    override val unconfined: CoroutineDispatcher by lazy { Dispatchers.Main }
}
