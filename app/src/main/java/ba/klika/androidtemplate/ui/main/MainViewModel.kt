package ba.klika.androidtemplate.ui.main

import ba.klika.androidtemplate.data.session.SessionRepository
import ba.klika.androidtemplate.scheduling.DispatcherProvider
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import ba.klika.androidtemplate.ui.base.viewmodel.SingleLiveEvent
import javax.inject.Inject

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class MainViewModel
@Inject constructor(
    private val sessionRepository: SessionRepository,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    val landingNavigationTrigger = SingleLiveEvent<Void>()

    fun onLogOutClick() {
        runIo {
            sessionRepository.logOut()
            runOnMain {
                landingNavigationTrigger.call()
            }
        }
    }
}
