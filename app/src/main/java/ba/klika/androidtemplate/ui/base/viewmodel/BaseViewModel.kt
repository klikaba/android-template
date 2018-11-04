package ba.klika.androidtemplate.ui.base.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import ba.klika.androidtemplate.scheduling.SchedulingProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
abstract class BaseViewModel(protected val schedulingProvider: SchedulingProvider) : ViewModel(), LifecycleObserver {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected fun addDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    protected fun Disposable.disposeOnClear() = addDisposable(this@disposeOnClear)

    protected fun <T> Single<T>.asIOCall() = this@asIOCall.observeOn(schedulingProvider.main())
            .subscribeOn(schedulingProvider.io())

    protected fun Completable.asIOCall() = this@asIOCall.observeOn(schedulingProvider.main())
            .subscribeOn(schedulingProvider.io())

    protected fun <T> Maybe<T>.asIOCall() = this@asIOCall.observeOn(schedulingProvider.main())
            .subscribeOn(schedulingProvider.io())

    protected fun <T> Flowable<T>.asIOCall() = this@asIOCall.observeOn(schedulingProvider.main())
            .subscribeOn(schedulingProvider.io())

    protected fun <T> Observable<T>.asIOCall() = this@asIOCall.observeOn(schedulingProvider.main())
            .subscribeOn(schedulingProvider.io())

    /**
     * Ensures that all added disposables are properly disposed
     * Make sure to call super due to this!
     */
    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}