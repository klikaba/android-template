package ba.klika.androidtemplate.ui.base.di.component

import ba.klika.androidtemplate.App
import ba.klika.androidtemplate.data.base.di.module.CacheModule
import ba.klika.androidtemplate.data.base.di.module.NetworkModule
import ba.klika.androidtemplate.scheduling.AndroidSchedulingModule
import ba.klika.androidtemplate.ui.base.di.module.ActivityBuilder
import ba.klika.androidtemplate.ui.base.di.module.AppModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Component(modules = [
    AndroidSupportInjectionModule::class,
    NetworkModule::class,
    CacheModule::class,
    AppModule::class,
    AndroidSchedulingModule::class,
    ActivityBuilder::class
])
@Singleton
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}
