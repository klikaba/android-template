package ba.klika.androidtemplate.ui.base.view

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import dagger.android.support.DaggerAppCompatActivity

/**
 * Base activity for all activities
 * @author Ensar Sarajčić <ensar.sarajcic@klika.ba>.
 */
abstract class BaseActivity : DaggerAppCompatActivity(), LifecycleOwner {
    /**
     * Provides layout id of this view
     */
    @get:LayoutRes
    abstract val layoutRId: Int

    private var viewDataBinding: ViewDataBinding? = null

    /**
     * Handles inflation and view binding
     * Don't forget to call super due to this!
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        doInjections()

        preInflate()
        if (layoutRId != 0) {
            viewDataBinding = DataBindingUtil.setContentView(this, layoutRId)
        }
        postInflate(viewDataBinding)
    }

    /**
     * Invoked before inflating the view
     * Called even when view won't be inflated if layout id is 0
     */
    protected open fun preInflate() {}

    /**
     * Invoked just after inflating the view
     * Called even when view was not inflated if layout id was 0
     * Override to add variables, etc.
     */
    protected open fun postInflate(viewDataBinding: ViewDataBinding?) { }

    /**
     * Override this if you want to inject lifecycle delegates prior to your onCreate() code
     * This will be called only once (in onCreate() method)
     * Don't forget to call super
     */
    protected open fun doInjections() {
    }
}
