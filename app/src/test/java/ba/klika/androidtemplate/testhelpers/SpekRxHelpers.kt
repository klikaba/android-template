package ba.klika.androidtemplate.testhelpers

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.spekframework.spek2.dsl.Skip
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.style.specification.Suite

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
inline fun Suite.rxGroup(description: String, skip: Skip = Skip.No, crossinline body: Suite.() -> Unit) {
    delegate.group(description, skip, CachingMode.TEST) {
        this@rxGroup.beforeEach {
            RxJavaPlugins.reset()
            RxAndroidPlugins.reset()
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setInitSingleSchedulerHandler { Schedulers.trampoline() }
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
        }
        this@rxGroup.body()
        this@rxGroup.afterEach {
            RxJavaPlugins.reset()
            RxAndroidPlugins.reset()
        }
    }
}