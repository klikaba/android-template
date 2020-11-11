package ba.klika.androidtemplate.ui.landing

import android.view.View
import ba.klika.androidtemplate.scheduling.DispatcherProvider
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import ba.klika.androidtemplate.ui.base.viewmodel.SingleLiveEvent
import javax.inject.Inject

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class LandingViewModel
@Inject constructor(dispatcherProvider: DispatcherProvider) : BaseViewModel(dispatcherProvider) {
    enum class NavigationEvent {
        LOGIN,
        REGISTRATION
    }

    val navigationTrigger = SingleLiveEvent<NavigationEvent>()

    fun registerClick(view: View) {
        navigationTrigger.value = NavigationEvent.REGISTRATION
    }

    fun loginClick(view: View) {
        navigationTrigger.value = NavigationEvent.LOGIN
    }
}