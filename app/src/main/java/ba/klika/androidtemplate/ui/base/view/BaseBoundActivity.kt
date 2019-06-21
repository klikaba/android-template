package ba.klika.androidtemplate.ui.base.view

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import javax.inject.Inject

/**
 * Base activity for all activities with viewmodels
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
abstract class BaseBoundActivity<VIEW_MODEL_TYPE : BaseViewModel>
    : BaseActivity(), BoundView<VIEW_MODEL_TYPE> {

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    final override lateinit var viewModel: VIEW_MODEL_TYPE

    /**
     * Prepares viewmodel before inflating
     * Don't forget to call super due to this!
     */
    override fun preInflate() {
        super.preInflate()
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
        lifecycle.addObserver(viewModel)
    }

    /**
     * Binds viewmodel after inflating
     * Don't forget to call super due to this!
     */
    override fun postInflate(viewDataBinding: ViewDataBinding?) {
        super.postInflate(viewDataBinding)
        viewDataBinding?.let {
            val viewModelRId = viewModelNameRId
            if (viewModelRId != 0) {
                it.setVariable(viewModelRId, viewModel)
                it.lifecycleOwner = this
                it.executePendingBindings()
            }
        }

        bindToViewModel()
    }
}