package ba.klika.androidtemplate.ui.landing.registration

import androidx.lifecycle.MutableLiveData
import ba.klika.androidtemplate.data.session.Credentials
import ba.klika.androidtemplate.data.session.SessionRepository
import ba.klika.androidtemplate.data.user.RegistrationInfo
import ba.klika.androidtemplate.data.user.UserRepository
import ba.klika.androidtemplate.scheduling.SchedulingProvider
import ba.klika.androidtemplate.ui.base.SimpleNavigationAction
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import ba.klika.androidtemplate.ui.base.viewmodel.SingleLiveEvent
import javax.inject.Inject

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class RegistrationViewModel
@Inject constructor(
        private val userRepository: UserRepository,
        private val sessionRepository: SessionRepository,
        schedulingProvider: SchedulingProvider): BaseViewModel(schedulingProvider) {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val navigationTrigger = SingleLiveEvent<SimpleNavigationAction>()

    fun onRegisterClick() {
        val usernameValue = username.value
        val passwordValue = password.value
        userRepository.create(
                RegistrationInfo(usernameValue!!, passwordValue!!)
        ).flatMapCompletable {
            sessionRepository.logIn(
                    Credentials(usernameValue, passwordValue)
            )
        }.asIOCall().subscribe(
            { navigationTrigger.postValue(SimpleNavigationAction.NEXT) },
            { it.printStackTrace() }
        ).disposeOnClear()
    }
}