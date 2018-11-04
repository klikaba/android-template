package ba.klika.androidtemplate.ui.landing

import ba.klika.androidtemplate.R
import ba.klika.androidtemplate.ui.base.di.FragmentScope
import ba.klika.androidtemplate.ui.base.view.BaseActivity
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import ba.klika.androidtemplate.ui.landing.login.LoginFragment
import ba.klika.androidtemplate.ui.landing.login.LoginModule
import ba.klika.androidtemplate.ui.landing.login.LoginViewModel
import ba.klika.androidtemplate.ui.landing.registration.RegisterModule
import ba.klika.androidtemplate.ui.landing.registration.RegistrationFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class LandingActivity : BaseActivity<BaseViewModel>() {
    override val layoutRId: Int
        get() = R.layout.activity_landing
    override val viewModelNameRId: Int
        get() = 0
    override val viewModelClass: Class<BaseViewModel>
        get() = BaseViewModel::class.java

    override fun bindToViewModel() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

@Module
abstract class LandingHostModule

@Module
abstract class LandingFragmentBuilder {

    @FragmentScope
    @ContributesAndroidInjector(modules = [LoginModule::class])
    abstract fun provideLoginFragment(): LoginFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [RegisterModule::class])
    abstract fun provideRegisterFragment(): RegistrationFragment
}