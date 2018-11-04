package ba.klika.androidtemplate.ui.landing.registration

import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import ba.klika.androidtemplate.BR
import ba.klika.androidtemplate.R
import ba.klika.androidtemplate.ui.base.SimpleNavigationAction
import ba.klika.androidtemplate.ui.base.di.viewmodel.ViewModelKey
import ba.klika.androidtemplate.ui.base.view.BaseBoundFragment
import ba.klika.androidtemplate.ui.main.MainActivity
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class RegistrationFragment : BaseBoundFragment<RegistrationViewModel>() {
    override val layoutRId: Int
        get() = R.layout.fragment_registration
    override val viewModelNameRId: Int
        get() = BR.viewModel
    override val viewModelClass: Class<RegistrationViewModel>
        get() = RegistrationViewModel::class.java

    override fun bindToViewModel() {
        viewModel.navigationTrigger.observe(viewLifecycleOwner, Observer {
            when(it) {
                SimpleNavigationAction.NEXT -> {
                    activity?.startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()
                }
                SimpleNavigationAction.BACK -> activity?.onBackPressed()
            }
        })
    }
}

@Module
abstract class RegisterModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    abstract fun provideRegisterViewModel(registrationViewModel: RegistrationViewModel): ViewModel
}
