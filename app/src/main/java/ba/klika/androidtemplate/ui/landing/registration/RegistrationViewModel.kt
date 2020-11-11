package ba.klika.androidtemplate.ui.landing.registration

import androidx.lifecycle.MutableLiveData
import ba.klika.androidtemplate.data.session.Credentials
import ba.klika.androidtemplate.data.session.SessionRepository
import ba.klika.androidtemplate.data.user.RegistrationInfo
import ba.klika.androidtemplate.data.user.UserRepository
import ba.klika.androidtemplate.scheduling.DispatcherProvider
import ba.klika.androidtemplate.ui.base.SimpleNavigationAction
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import ba.klika.androidtemplate.ui.base.viewmodel.SingleLiveEvent
import java.lang.Exception
import javax.inject.Inject

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class RegistrationViewModel
@Inject constructor(
    private val userRepository: UserRepository,
    private val sessionRepository: SessionRepository,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val navigationTrigger = SingleLiveEvent<SimpleNavigationAction>()
    val toastMessage = MutableLiveData<String>()

    fun onRegisterClick() {
        val usernameValue = username.value
        val passwordValue = password.value
        runIo {
            try {
                userRepository.create(RegistrationInfo(usernameValue!!, passwordValue!!))
                sessionRepository.logIn(Credentials(usernameValue, passwordValue))
                navigationTrigger.postValue(SimpleNavigationAction.NEXT)
            } catch (e: Exception) {
                e.printStackTrace()
                toastMessage.postValue("Failed")
            }
        }
    }
}
