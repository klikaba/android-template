package ba.klika.androidtemplate.ui.landing

import android.view.View
import androidx.lifecycle.MutableLiveData
import ba.klika.androidtemplate.scheduling.SchedulingProvider
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import ba.klika.androidtemplate.ui.base.viewmodel.SingleLiveEvent
import javax.inject.Inject

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class LandingViewModel
@Inject constructor(schedulingProvider: SchedulingProvider) : BaseViewModel(schedulingProvider) {
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