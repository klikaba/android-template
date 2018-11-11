package ba.klika.androidtemplate.ui.base.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.android.support.DaggerFragment

/**
 * Base fragment for all fragments
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
abstract class BaseFragment : DaggerFragment() {
    /**
     * Provides layout id of this view
     * May not be 0!
     */
    @get:LayoutRes
    abstract val layoutRId: Int

    private lateinit var viewDataBinding: ViewDataBinding

    /**
     * Inflates layout
     * Don't forget to call super due to this
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        preInflate()
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutRId, container, false)
        return viewDataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postInflate(viewDataBinding)
    }

    /**
     * Invoked before inflating the view
     */
    protected open fun preInflate() {}

    /**
     * Invoked just after inflating the view
     * Override to add variables, etc.
     */
    protected open fun postInflate(viewDataBinding: ViewDataBinding?) { }
}