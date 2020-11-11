package ba.klika.androidtemplate.ui.landing.login

import androidx.lifecycle.MutableLiveData
import ba.klika.androidtemplate.data.session.Credentials
import ba.klika.androidtemplate.data.session.SessionRepository
import ba.klika.androidtemplate.scheduling.DispatcherProvider
import ba.klika.androidtemplate.ui.base.SimpleNavigationAction
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import ba.klika.androidtemplate.ui.base.viewmodel.SingleLiveEvent
import javax.inject.Inject

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class LoginViewModel
@Inject constructor(
    private val sessionRepository: SessionRepository,
    dispatcherProvider: DispatcherProvider
) : BaseViewModel(dispatcherProvider) {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val navigationTrigger = SingleLiveEvent<SimpleNavigationAction>()
    val toastMessage = MutableLiveData<String>()

    fun onLoginClick() {
        runIo {
            try {
                sessionRepository.logIn(Credentials(username.value!!, password.value!!))
                navigationTrigger.postValue(SimpleNavigationAction.NEXT)
            } catch (e: Exception) {
                e.printStackTrace()
                toastMessage.postValue("Failed")
            }
        }
    }
}
