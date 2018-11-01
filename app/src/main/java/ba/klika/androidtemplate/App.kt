package ba.klika.androidtemplate

import android.app.Activity
import ba.klika.androidtemplate.ui.base.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class App : DaggerApplication() {

    @JvmField
    @Inject
    var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>? = null

    override fun applicationInjector(): AndroidInjector<App> {
        return DaggerAppComponent.builder().create(this)
    }
}