package ba.klika.androidtemplate.ui.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import ba.klika.androidtemplate.ui.base.viewmodel.BaseViewModel
import dagger.android.support.DaggerFragment

/**
 * Base for all kotlin fragments
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
abstract class BaseFragment<out VIEW_MODEL_TYPE : BaseViewModel> : DaggerFragment(), BoundView<VIEW_MODEL_TYPE> {

    private lateinit var viewDataBinding: ViewDataBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutRId, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModelRId = viewModelNameRId
        if (viewModelRId != 0) {
            viewDataBinding.setVariable(viewModelRId, viewModel)
            viewDataBinding.setLifecycleOwner(this)
            viewDataBinding.executePendingBindings()
        }

        bindToViewModel()
    }
}