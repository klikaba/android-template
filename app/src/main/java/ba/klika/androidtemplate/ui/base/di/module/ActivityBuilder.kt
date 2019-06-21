package ba.klika.androidtemplate.ui.base.di.module

import ba.klika.androidtemplate.ui.base.di.ActivityScope
import ba.klika.androidtemplate.ui.landing.LandingActivity
import ba.klika.androidtemplate.ui.landing.LandingFragmentBuilder
import ba.klika.androidtemplate.ui.landing.LandingHostModule
import ba.klika.androidtemplate.ui.main.MainActivity
import ba.klika.androidtemplate.ui.main.MainFragmentBuilder
import ba.klika.androidtemplate.ui.main.MainModule
import ba.klika.androidtemplate.ui.splash.SplashActivity
import ba.klika.androidtemplate.ui.splash.SplashModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Activities and their components should be provided in this file
 *
 * This may be further modularized, but all such modules should be added to the [ba.klika.androidtemplate.ui.base.AppComponent]
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun provideSplashActivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [LandingHostModule::class, LandingFragmentBuilder::class])
    abstract fun provideLandingActivity(): LandingActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [MainModule::class, MainFragmentBuilder::class])
    abstract fun provideMainActivity(): MainActivity
}
