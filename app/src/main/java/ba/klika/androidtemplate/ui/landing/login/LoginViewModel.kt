package ba.klika.androidtemplate.ui.landing.login

import androidx.lifecycle.MutableLiveData
import ba.klika.androidtemplate.data.session.Credentials
import ba.klika.androidtemplate.data.session.SessionRepository
import ba.klika.androidtemplate.ui.base.SimpleNavigationAction
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import ba.klika.androidtemplate.ui.base.viewmodel.SingleLiveEvent
import javax.inject.Inject

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class LoginViewModel
@Inject constructor(private val sessionRepository: SessionRepository) : BaseViewModel() {

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val navigationTrigger = SingleLiveEvent<SimpleNavigationAction>()

    fun onLoginClick() {
        sessionRepository.logIn(
                Credentials(username.value!!, password.value!!)
        ).subscribe {
            navigationTrigger.postValue(SimpleNavigationAction.NEXT)
        }.disposeOnClear()
    }
}