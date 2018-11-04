package ba.klika.androidtemplate.ui.splash

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import ba.klika.androidtemplate.data.session.SessionRepository
import ba.klika.androidtemplate.scheduling.SchedulingProvider
import ba.klika.androidtemplate.ui.base.di.viewmodel.ViewModelKey
import ba.klika.androidtemplate.ui.base.view.BaseBoundActivity
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import ba.klika.androidtemplate.ui.base.viewmodel.SingleLiveEvent
import ba.klika.androidtemplate.ui.landing.LandingActivity
import ba.klika.androidtemplate.ui.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject

class SplashActivity : BaseBoundActivity<SplashViewModel>() {
    override val layoutRId: Int
        get() = 0
    override val viewModelNameRId: Int
        get() = 0
    override val viewModelClass: Class<SplashViewModel>
        get() = SplashViewModel::class.java

    override fun bindToViewModel() {
        viewModel.navigationEvent.observe(this, Observer {
            when(it) {
                SplashViewModel.NavigationEvent.MAIN ->
                    startActivity(Intent(this, MainActivity::class.java))
                SplashViewModel.NavigationEvent.LANDING ->
                    startActivity(Intent(this, LandingActivity::class.java))
            }
        })
    }
}

class SplashViewModel
@Inject constructor(
        private val sessionRepository: SessionRepository,
        schedulingProvider: SchedulingProvider) : BaseViewModel(schedulingProvider) {

    enum class NavigationEvent {
        MAIN,
        LANDING
    }

    val navigationEvent = SingleLiveEvent<NavigationEvent>()

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