package ba.klika.androidtemplate.ui.base.view

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import dagger.android.support.DaggerAppCompatActivity

/**
 * Base activity for all activities
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
abstract class BaseActivity<out VIEW_MODEL_TYPE : BaseViewModel>
    : DaggerAppCompatActivity(), BoundView<VIEW_MODEL_TYPE> {

    private lateinit var viewDataBinding: ViewDataBinding

    /**
     * Handles intents, injects lifecycle delegates and updates them
     * Don't forget to call super due to this!
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        doInjections()

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