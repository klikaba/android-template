package ba.klika.androidtemplate.ui.base.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ba.klika.androidtemplate.scheduling.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import kotlinx.coroutines.withContext

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
abstract class BaseViewModel(protected val dispatcherProvider: DispatcherProvider) : ViewModel(), LifecycleObserver {
    protected fun runOn(coroutineDispatcher: CoroutineDispatcher, block: suspend CoroutineScope.() -> Unit) {
        (viewModelScope + coroutineDispatcher).launch {
            block()
        }
    }

    protected fun runIo(block: suspend CoroutineScope.() -> Unit) = runOn(dispatcherProvider.io(), block)

    protected fun runComputation(block: suspend CoroutineScope.() -> Unit) = runOn(dispatcherProvider.computation(), block)
    protected suspend fun CoroutineScope.runOnMain(block: CoroutineScope.() -> Unit) = withContext(dispatcherProvider.main()) {
        block()
    }
}
