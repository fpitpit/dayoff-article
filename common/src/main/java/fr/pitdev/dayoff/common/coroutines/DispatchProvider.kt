package fr.pitdev.dayoff.common.coroutines

import androidx.annotation.VisibleForTesting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

open class CoroutineDispatcherProvider {
    open val main: CoroutineDispatcher by lazy { Dispatchers.Main }
    open val io: CoroutineDispatcher by lazy { Dispatchers.IO }
    open val default: CoroutineDispatcher by lazy { Dispatchers.Default }
    open val unconfined: CoroutineDispatcher by lazy { Dispatchers.Unconfined }
}

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
class TestCoroutineDispatcherProvider : CoroutineDispatcherProvider() {
    override val main: CoroutineDispatcher by lazy { Dispatchers.Unconfined }
    override val io: CoroutineDispatcher by lazy { Dispatchers.Unconfined }
    override val default: CoroutineDispatcher by lazy { Dispatchers.Unconfined }
    override val unconfined: CoroutineDispatcher by lazy { Dispatchers.Unconfined }
}
