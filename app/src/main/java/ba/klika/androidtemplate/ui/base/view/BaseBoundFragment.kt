package ba.klika.androidtemplate.ui.base.view

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * Base for all kotlin fragments with viewmodels
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
abstract class BaseBoundFragment<VIEW_MODEL_TYPE : BaseViewModel> : BaseFragment(), BoundView<VIEW_MODEL_TYPE> {

    /**
     * Use this if you need to get activity view model
     * (ViewModelProviders.of(activity, viewModelFactory).get(activityViewModelClass))
     *
     * That way it will use the injected instance of ViewModel
     */
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    final override lateinit var viewModel: VIEW_MODEL_TYPE

    override fun postInflate(viewDataBinding: ViewDataBinding?) {
        super.postInflate(viewDataBinding)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
        lifecycle.addObserver(viewModel)

        viewDataBinding?.let {
            val viewModelRId = viewModelNameRId
            if (viewModelRId != 0) {
                it.setVariable(viewModelRId, viewModel)
                it.setLifecycleOwner(viewLifecycleOwner)
                it.executePendingBindings()
            }
        }

        bindToViewModel()
    }
}