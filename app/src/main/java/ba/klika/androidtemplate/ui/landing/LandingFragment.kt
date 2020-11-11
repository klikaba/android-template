package ba.klika.androidtemplate.ui.landing

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.NavHostFragment
import ba.klika.androidtemplate.BR
import ba.klika.androidtemplate.R
import ba.klika.androidtemplate.ui.base.di.viewmodel.ViewModelKey
import ba.klika.androidtemplate.ui.base.view.BaseBoundFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class LandingFragment : BaseBoundFragment<LandingViewModel>() {
    override val layoutRId: Int
        get() = R.layout.fragment_landing
    override val viewModelNameRId: Int
        get() = BR.viewModel
    override val viewModelClass: Class<LandingViewModel>
        get() = LandingViewModel::class.java

    override fun bindToViewModel() {
        viewModel.navigationTrigger.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    LandingViewModel.NavigationEvent.LOGIN -> NavHostFragment.findNavController(this).navigate(R.id.action_landing_to_login)
                    LandingViewModel.NavigationEvent.REGISTRATION -> NavHostFragment.findNavController(this).navigate(R.id.action_landing_to_registration)
                    null -> { }
                }
            }
        )
    }
}

@Module
abstract class LandingModule {

    @Binds
    @IntoMap
    @ViewModelKey(LandingViewModel::class)
    abstract fun provideLandingViewModel(landingViewModel: LandingViewModel): ViewModel
}
