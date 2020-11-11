package ba.klika.androidtemplate.ui.base.di.component

import ba.klika.androidtemplate.App
import ba.klika.androidtemplate.data.auth.oauth2.OAuth2Module
import ba.klika.androidtemplate.data.base.di.module.CacheModule
import ba.klika.androidtemplate.data.base.di.module.NetworkModule
import ba.klika.androidtemplate.data.country.CountriesDataModule
import ba.klika.androidtemplate.data.session.SessionModule
import ba.klika.androidtemplate.data.user.UserDataModule
import ba.klika.androidtemplate.scheduling.AndroidSchedulingModule
import ba.klika.androidtemplate.ui.base.di.module.ActivityBuilder
import ba.klika.androidtemplate.ui.base.di.module.AppConfigModule
import ba.klika.androidtemplate.ui.base.di.module.AppModule
import ba.klika.androidtemplate.ui.base.di.viewmodel.ViewModelFactoryModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Component(
    modules = [
        // Android
        AndroidSupportInjectionModule::class,
        AndroidSchedulingModule::class,

        // Application
        AppModule::class,
        ActivityBuilder::class,
        ViewModelFactoryModule::class,

        // Config
        AppConfigModule::class,

        // Data
        NetworkModule::class,
        CacheModule::class,
        OAuth2Module::class,
        SessionModule::class,
        CountriesDataModule::class,
        UserDataModule::class
    ]
)
@Singleton
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}
