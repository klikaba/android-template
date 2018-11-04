package ba.klika.androidtemplate.ui.base.view

import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel

/**
 * Represents a View that can be bound to a ViewModel
 *
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
interface BoundView<VIEW_MODEL_TYPE : BaseViewModel> {
    /**
     * Provides name of view model (BR.name)
     */
    val viewModelNameRId: Int

    /**
     * Provides viewmodel of this view
     * ViewModel will be injected into the view and returned from this method
     */
    var viewModel: VIEW_MODEL_TYPE

    /**
     * Provides class of this views ViewModel
     * This is required to properly inject the ViewModel
     */
    val viewModelClass: Class<VIEW_MODEL_TYPE>

    /**
     * Invoked once everything is ready
     * Use this method to complete binding to view model
     * (all bindings that were not possible through XML)
     */
    fun bindToViewModel()
}