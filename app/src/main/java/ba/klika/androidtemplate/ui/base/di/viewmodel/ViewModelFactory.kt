package ba.klika.androidtemplate.ui.base.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.Multibinds
import javax.inject.Inject
import javax.inject.Provider
import kotlin.IllegalArgumentException
import kotlin.reflect.KClass

/**
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
class ViewModelFactory
@Inject constructor(private val viewModelProviders: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelProvider = viewModelProviders[modelClass] ?: throw IllegalArgumentException("Missing provider for class ${modelClass.canonicalName}")
        val providedViewModel = viewModelProvider.get() ?: throw NullPointerException("Provider for ViewModel may not return null! (provider for ${modelClass.canonicalName}")
        if (modelClass.isInstance(providedViewModel)) {
            return providedViewModel as T
        } else {
            throw ClassCastException("Provider for ${modelClass.canonicalName} returned ViewModel of wrong type")
        }
    }
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelFactoryModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Multibinds
    abstract fun bindViewModelProviders(): Map<Class<out ViewModel>, ViewModel>
}
