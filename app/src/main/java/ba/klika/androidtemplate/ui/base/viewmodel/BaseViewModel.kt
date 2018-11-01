package ba.klika.androidtemplate.ui.base.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
abstract class BaseViewModel : ViewModel(), LifecycleObserver {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    protected fun Disposable.addToCompositeDisposable() = compositeDisposable.add(this@addToCompositeDisposable)

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}