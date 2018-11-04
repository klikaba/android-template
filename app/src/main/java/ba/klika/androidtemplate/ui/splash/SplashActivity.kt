package ba.klika.androidtemplate.ui.splash

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import ba.klika.androidtemplate.data.session.SessionRepository
import ba.klika.androidtemplate.ui.base.di.viewmodel.ViewModelKey
import ba.klika.androidtemplate.ui.base.view.BaseActivity
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import ba.klika.androidtemplate.ui.base.viewmodel.SingleLiveEvent
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Singleton

class SplashActivity : BaseActivity<BaseViewModel>() {
    override val layoutRId: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val viewModelNameRId: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
    override val viewModelClass: Class<BaseViewModel>
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun bindToViewModel() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class SplashViewModel
@Inject constructor(private val sessionRepository: SessionRepository) : BaseViewModel() {

    enum class NavigationEvent {
        MAIN,
        LANDING
    }

    private val navigationEvent = SingleLiveEvent<NavigationEvent>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onStart() {
        sessionRepository.hasSession().subscribe { it: Boolean ->
            if (it) {
                navigationEvent.postValue(NavigationEvent.MAIN)
            } else {
                navigationEvent.postValue(NavigationEvent.LANDING)
            }
        }.disposeOnClear()
    }
}

@Module
abstract class SplashModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun provideSplashViewModel(splashViewModel: SplashViewModel): ViewModel
}