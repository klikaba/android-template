package ba.klika.androidtemplate.ui.base.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.IllegalArgumentException
import kotlin.reflect.KClass

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
@Singleton
class ViewModelFactory
@Inject constructor(private val viewModelProviders: MutableMap<Class<out ViewModel?>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModelProvider = viewModelProviders[modelClass] ?: throw IllegalArgumentException("Missing provider for class ${modelClass.canonicalName}")
        val providedViewModel = viewModelProvider.get() ?: throw NullPointerException("Provider for ViewModel may not return null! (provider for ${modelClass.canonicalName}")
        if (modelClass.isInstance(providedViewModel)) {
            return providedViewModel as T
        } else {
            throw ClassCastException("Provider for ${modelClass.canonicalName} returned ViewModel of wrong type")
        }
    }
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out BaseViewModel>)

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}