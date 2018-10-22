package ba.klika.androidtemplate.ui.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Base for all kotlin fragments
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
abstract class BaseFragment<VIEW_MODEL_TYPE : BaseViewModel> : DaggerFragment(), BoundView<VIEW_MODEL_TYPE> {

    private lateinit var viewDataBinding: ViewDataBinding

    /**
     * Use this if you need to get activity view model
     * (ViewModelProviders.of(activity, viewModelFactory).get(activityViewModelClass))
     *
     * That way it will use the injected instance of ViewModel
     */
    @Inject
    protected lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutRId, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)

        val viewModelRId = viewModelNameRId
        if (viewModelRId != 0) {
            viewDataBinding.setVariable(viewModelRId, viewModel)
            viewDataBinding.setLifecycleOwner(this)
            viewDataBinding.executePendingBindings()
        }

        bindToViewModel()
    }
}