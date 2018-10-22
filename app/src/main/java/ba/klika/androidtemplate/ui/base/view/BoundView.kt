package ba.klika.androidtemplate.ui.base.view

import androidx.annotation.LayoutRes
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel

/**
 * Represents a View that can be bound to a ViewModel
 *
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface BoundView<out VIEW_MODEL_TYPE : BaseViewModel> {
    /**
     * Provides layout id of this view
     */
    @get:LayoutRes
    val layoutRId: Int

    /**
     * Provides name of view model (BR.name)
     */
    val viewModelNameRId: Int

    /**
     * Provides viewmodel of this activity
     * ViewModel should be injected into the activity and returned from this method
     */
    val viewModel: VIEW_MODEL_TYPE

    /**
     * Invoked once everything is ready
     * Use this method to complete binding to view model
     * (all bindings that were not possible through XML)
     */
    fun bindToViewModel()
}