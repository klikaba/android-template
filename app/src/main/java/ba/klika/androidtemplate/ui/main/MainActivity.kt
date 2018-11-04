package ba.klika.androidtemplate.ui.main

import ba.klika.androidtemplate.R
import ba.klika.androidtemplate.ui.base.di.FragmentScope
import ba.klika.androidtemplate.ui.base.view.BaseActivity
import ba.klika.androidtemplate.ui.main.country.CountriesFragment
import ba.klika.androidtemplate.ui.main.country.CountriesModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class MainActivity : BaseActivity() {
    override val layoutRId: Int
        get() = R.layout.activity_main
}

@Module
abstract class MainModule

@Module
abstract class MainFragmentBuilder {
    @FragmentScope
    @ContributesAndroidInjector(modules = [CountriesModule::class])
    abstract fun provideCountriesFragment(): CountriesFragment
}