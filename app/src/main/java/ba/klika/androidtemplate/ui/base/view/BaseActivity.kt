package ba.klika.androidtemplate.ui.base.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Base activity for all activities
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
abstract class BaseActivity<VIEW_MODEL_TYPE : BaseViewModel>
    : DaggerAppCompatActivity(), BoundView<VIEW_MODEL_TYPE> {

    private lateinit var viewDataBinding: ViewDataBinding

    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    final override lateinit var viewModel: VIEW_MODEL_TYPE

    /**
     * Handles intents, injects lifecycle delegates and updates them
     * Don't forget to call super due to this!
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        doInjections()

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
        lifecycle.addObserver(viewModel)

        viewDataBinding = DataBindingUtil.setContentView<ViewDataBinding>(this, layoutRId)

        val viewModelRId = viewModelNameRId
        if (viewModelRId != 0) {
            viewDataBinding.setVariable(viewModelRId, viewModel)
            viewDataBinding.setLifecycleOwner(this)
            viewDataBinding.executePendingBindings()
        }

        bindToViewModel()
    }

    /**
     * Override this if you want to inject lifecycle delegates prior to your onCreate() code
     * This will be called only once (in onCreate() method)
     * Don't forget to call super
     */
    protected open fun doInjections() {
    }
}