package ba.klika.androidtemplate.ui.landing.login

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import ba.klika.androidtemplate.BR
import ba.klika.androidtemplate.R
import ba.klika.androidtemplate.ui.base.SimpleNavigationAction
import ba.klika.androidtemplate.ui.base.di.viewmodel.ViewModelKey
import ba.klika.androidtemplate.ui.base.view.BaseBoundFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class LoginFragment : BaseBoundFragment<LoginViewModel>() {
    override val layoutRId: Int
        get() = R.layout.fragment_login
    override val viewModelNameRId: Int
        get() = BR.viewModel
    override val viewModelClass: Class<LoginViewModel>
        get() = LoginViewModel::class.java

    override fun bindToViewModel() {
        viewModel.navigationTrigger.observe(viewLifecycleOwner, Observer {
            when (it) {
                SimpleNavigationAction.NEXT -> {}
                SimpleNavigationAction.BACK -> {}
            }
        })
    }
}

@Module
abstract class LoginModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun provideLoginViewModel(loginViewModel: LoginViewModel): ViewModel
}